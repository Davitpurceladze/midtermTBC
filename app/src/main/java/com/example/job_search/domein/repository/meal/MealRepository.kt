package com.example.job_search.domein.repository.meal

import com.example.job_search.data.common.Resource
import com.example.job_search.domein.model.meal.GetMeal
import com.example.job_search.domein.model.meal.GetMealByIngredients
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    suspend fun getSingleRandomMeal(): Flow<Resource<List<GetMeal>>>
    suspend fun getMealByName(name: String): Flow<Resource<List<GetMeal>>>
    suspend fun getMealByIngredients(ingredient: String): Flow<Resource<List<GetMealByIngredients>>>
    suspend fun getMealById(id:String): Flow<Resource<List<GetMeal>>>
}