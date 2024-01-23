package com.example.job_search.di

import com.example.job_search.data.common.HandleMealResponse
import com.example.job_search.data.repository.auth.AuthRepositoryImpl
import com.example.job_search.data.repository.meal.MealRepositoryImpl
import com.example.job_search.data.service.meal.MealService
import com.example.job_search.domein.repository.auth.AuthRepository
import com.example.job_search.domein.repository.meal.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMealRepository (mealService: MealService, handleMealResponse: HandleMealResponse) : MealRepository {
        return MealRepositoryImpl(mealService, handleMealResponse)
    }

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl
}