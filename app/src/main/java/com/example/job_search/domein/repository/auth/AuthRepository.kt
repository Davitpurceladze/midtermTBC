package com.example.job_search.domein.repository.auth

import com.example.job_search.data.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Flow<Resource<Unit>>
    suspend fun registration(email: String, password: String): Flow<Resource<Unit>>
    fun logout()
}