package com.example.job_search.domein.usecase.registration

import com.example.job_search.data.common.Resource
import com.example.job_search.domein.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Unit>> = authRepository.registration(email, password)
}