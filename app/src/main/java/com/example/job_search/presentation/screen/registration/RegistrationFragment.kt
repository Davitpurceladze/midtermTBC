package com.example.job_search.presentation.screen.registration

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.job_search.databinding.FragmentRegistrationBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.registration.RegistrationEvent
import com.example.job_search.presentation.state.registration.RegistrationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()


    override fun bind() {

    }

    override fun bindViewActionListeners() {
//        binding.btnLogIn.setOnClickListener {
//            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLogInFragment())
//        }

        binding.btnRegistration.setOnClickListener {
            registerUser()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                 viewModel.registrationState.collect{
                     handleRegistrationState(registrationState = it)
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

    private fun handleRegistrationState(registrationState: RegistrationState) {
        println("this is fragment registration State -> $registrationState")

    }

    private fun registerUser() {
        viewModel.onEvent(
            RegistrationEvent.Registration(
                email = binding.etRegistrationEmail.text.toString(),
                password = binding.etRegistrationPassword.text.toString()
            )
        )
    }

    private fun handleNavigationEvents(event: RegistrationViewModel.RegistrationUiEvent) {
        when (event) {
            is RegistrationViewModel.RegistrationUiEvent.NavigateToLogIn -> findNavController().navigate(
                RegistrationFragmentDirections.actionRegistrationFragmentToLogInFragment()
            )
        }
    }

}