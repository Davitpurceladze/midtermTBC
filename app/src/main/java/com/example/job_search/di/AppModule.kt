package com.example.job_search.di

import com.example.job_search.data.common.HandleAuthResponse
import com.example.job_search.data.repository.auth.AuthRepositoryImpl
import com.example.job_search.domein.repository.auth.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFireBaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideHandleAuthResponse() : HandleAuthResponse {
        return HandleAuthResponse()
    }
}