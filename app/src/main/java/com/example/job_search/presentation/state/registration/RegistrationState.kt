package com.example.job_search.presentation.state.registration

data class RegistrationState(
    val data: Unit? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
