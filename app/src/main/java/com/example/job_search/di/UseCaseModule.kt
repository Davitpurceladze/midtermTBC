package com.example.job_search.di

import com.example.job_search.domein.usecase.validator.EmailValidatorUseCase
import com.example.job_search.domein.usecase.validator.PasswordValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideEmailValidatorUseCase() : EmailValidatorUseCase {
        return EmailValidatorUseCase()
    }

    @Singleton
    @Provides
    fun providePasswordValidatorUseCase() : PasswordValidatorUseCase {
        return PasswordValidatorUseCase()
    }



}