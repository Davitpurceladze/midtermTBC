package com.example.job_search.presentation.state.logIn

data class LogInState(
    val data: Unit? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
