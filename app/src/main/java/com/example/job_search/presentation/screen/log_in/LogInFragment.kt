package com.example.job_search.presentation.screen.log_in

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.job_search.databinding.FragmentLogInBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.log_in.LogInEvent
import com.example.job_search.presentation.screen.registration.RegistrationViewModel
import com.example.job_search.presentation.state.logIn.LogInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()


    override fun bind() {
    }

    override fun bindViewActionListeners() {
        binding.btnRegistration.setOnClickListener {
             viewModel.onEvent(LogInEvent.NavigateToRegistration)
        }

        binding.btnLogIn.setOnClickListener {
            logInUser()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                 viewModel.logInState.collect{
                     handleState(it)
                 }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect{
                    handleNavigationEvents(it)
                }
            }
        }

    }

    private fun handleState(state: LogInState) {
        println("this is state in Login Fragment -> $state")
    }

    private fun  logInUser () {
        viewModel.onEvent(
            LogInEvent.LogIn(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleNavigationEvents(event: LogInViewModel.LogInUiEvent) {
        when(event) {
            is LogInViewModel.LogInUiEvent.NavigateToRegistration -> {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegistrationFragment())
            }

            is LogInViewModel.LogInUiEvent.NavigateToMealsByNameFragment -> {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMealsByNameFragment())
            }
        }

    }
}