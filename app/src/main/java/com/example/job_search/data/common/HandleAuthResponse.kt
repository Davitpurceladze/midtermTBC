package com.example.job_search.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HandleAuthResponse {
    suspend fun <T> safeAuthCall(call: suspend () -> T): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading(true))
        try {
            call()
            emit(Resource.Success(Unit))
        } catch (e: Throwable) {
            emit(Resource.Failure(e.message ?: ""))
        }
        emit(Resource.Loading(false))

    }
}