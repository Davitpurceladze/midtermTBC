package com.example.job_search.data.mapper.base

import com.example.job_search.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

 suspend fun <Dto: Any, Domain: Any> Flow<Resource<Dto>>.asResource(
     onSuccess: suspend (Dto) -> Domain
 ): Flow<Resource<Domain>> {
     return this.map {
         when (it) {
             is Resource.Success -> Resource.Success(result = onSuccess.invoke(it.result))
             is Resource.Failure -> Resource.Failure(errorMessage = it.errorMessage)
             is Resource.Loading -> Resource.Loading(isLoading = it.isLoading)
         }
     }
 }

