package com.example.job_search.presentation.event.log_in

sealed class LogInEvent {
    data class LogIn(val email: String, val password: String) : LogInEvent()
    object NavigateToRegistration :LogInEvent()
}