package com.example.job_search.data.common

import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HandleMealResponse {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(isLoading = true))
        try {
            val response  = call()
            val body = response.body()
            if(response.isSuccessful && body != null){
                emit(Resource.Success(result = body))
            } else {
                emit(Resource.Failure(errorMessage = response.errorBody()?.string() ?: ""))
            }
        } catch (e: Throwable){
            emit(Resource.Failure(errorMessage = e.message ?: ""))
        }
        emit(Resource.Loading(isLoading = false))
    }
}

