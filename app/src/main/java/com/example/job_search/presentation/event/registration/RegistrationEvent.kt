package com.example.job_search.presentation.event.registration

sealed class RegistrationEvent{
    data class Registration(val email: String, val password: String): RegistrationEvent()
}
