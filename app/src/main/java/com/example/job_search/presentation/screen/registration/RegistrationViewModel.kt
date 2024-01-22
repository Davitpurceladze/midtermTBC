package com.example.job_search.presentation.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_search.data.common.Resource
import com.example.job_search.domein.usecase.registration.RegistrationUseCase
import com.example.job_search.domein.usecase.validator.EmailValidatorUseCase
import com.example.job_search.domein.usecase.validator.PasswordValidatorUseCase
import com.example.job_search.presentation.event.registration.RegistrationEvent
import com.example.job_search.presentation.state.registration.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: PasswordValidatorUseCase,
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationState())
    val registrationState: SharedFlow<RegistrationState> = _registrationState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RegistrationUiEvent>()
    val uiEvent: SharedFlow<RegistrationUiEvent> get() = _uiEvent

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.Registration -> validateForm(
                email = event.email,
                password = event.password
            )
        }
    }

    private fun validateForm(email: String, password: String) {
        val isEmailValid = emailValidator(email)
        val isPasswordValid = passwordValidator(password)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid)
                .all { it }

        if (!areFieldsValid) {
            updateErrorMessage(message = "Fields are not valid!")
            return
        }

        registration(email = email, password = password)
    }

    private fun registration(email: String, password: String) {
        viewModelScope.launch {
            registrationUseCase(email, password).collect {
                when (it) {
                    is Resource.Loading -> _registrationState.update { currentState ->
                        currentState.copy(
                            isLoading = it.isLoading
                        )
                    }

                    is Resource.Success -> {
                        _registrationState.update { currentState -> currentState.copy(data = it.result) }
                        println("result should print while everything ok")
                        _uiEvent.emit(RegistrationUiEvent.NavigateToLogIn)
                    }

                    is Resource.Failure -> {
                        _registrationState.update { currentState -> currentState.copy(errorMessage = it.errorMessage) }
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(message: String) {
        _registrationState.update { currentState -> currentState.copy(errorMessage = message)  }
    }


    sealed interface RegistrationUiEvent {
        object NavigateToLogIn : RegistrationUiEvent
    }


}