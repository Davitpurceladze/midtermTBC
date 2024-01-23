package com.example.job_search.presentation.screen.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_search.data.common.Resource
import com.example.job_search.domein.repository.auth.AuthRepository
import com.example.job_search.domein.usecase.log_in.LogInUseCase
import com.example.job_search.domein.usecase.validator.EmailValidatorUseCase
import com.example.job_search.domein.usecase.validator.PasswordValidatorUseCase
import com.example.job_search.presentation.event.log_in.LogInEvent
import com.example.job_search.presentation.state.logIn.LogInState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: PasswordValidatorUseCase,
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private val _logInState = MutableStateFlow(LogInState())
    val logInState: SharedFlow<LogInState> = _logInState.asStateFlow()



    private val _uiEvent = MutableSharedFlow<LogInUiEvent>()
    val uiEvent: SharedFlow<LogInUiEvent> get() = _uiEvent

    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> validateForm(
                email = event.email,
                password = event.password
            )

            is LogInEvent.NavigateToRegistration -> navigateToRegistration()
        }
    }

    private fun validateForm(email: String, password: String) {
        val isEmailValid = emailValidator(email)
        val isPasswordValid = passwordValidator(password)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid)
                .all { it }

        if (!areFieldsValid) {
//            updateErrorMessage(message = "Fields are not valid!")
            return
        }

        logIn(email, password)
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email, password).collect {
                when (it) {
                    is Resource.Loading -> _logInState.update { currentState ->
                        currentState.copy(
                            isLoading = it.isLoading
                        )
                    }

                    is Resource.Success -> {
                        _logInState.update { currentState ->
                            currentState.copy(data = it.result)
                        }
                        _uiEvent.emit(LogInUiEvent.NavigateToMealsByNameFragment)

                    }

                    is Resource.Failure -> {
                        _logInState.update { currentState ->
                            currentState.copy(errorMessage = it.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToRegistration() {
        viewModelScope.launch {
            _uiEvent.emit(LogInUiEvent.NavigateToRegistration)
        }
    }

    val currentUser: FirebaseUser?
        get() = repository.currentUser


    sealed interface LogInUiEvent {
        object NavigateToMealsByNameFragment : LogInUiEvent
        object NavigateToRegistration : LogInUiEvent
    }
}