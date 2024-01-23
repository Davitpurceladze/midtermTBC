package com.example.job_search.data.service.meal

import com.example.job_search.data.model.meal.MealByIngredientsDto
import com.example.job_search.data.model.meal.MealDto
import com.example.job_search.data.model.meal.MealResponseDto
import com.example.job_search.data.model.meal.MealsByIngredientsResponseDto
import com.example.job_search.presentation.model.Meal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET("random.php")
    suspend fun getSingleRandomMeal() : Response<MealResponseDto>

    @GET("search.php")
    suspend fun getMealByName(@Query("s") name: String) : Response<MealResponseDto>

    @GET("filter.php?")
    suspend fun getMealByIngredients(@Query("i") ingredient: String) : Response<MealsByIngredientsResponseDto>

    @GET("lookup.php?")
    suspend fun getMealById(@Query("i") id: String) : Response<MealResponseDto>
}