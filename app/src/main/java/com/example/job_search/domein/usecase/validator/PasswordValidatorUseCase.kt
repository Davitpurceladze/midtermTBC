package com.example.job_search.domein.usecase.validator

class PasswordValidatorUseCase {
    operator fun invoke(password: String): Boolean = password.isNotBlank()

}