package com.example.job_search.data.repository.meal

import com.example.job_search.data.common.HandleMealResponse
import com.example.job_search.data.common.Resource
import com.example.job_search.data.mapper.base.asResource
import com.example.job_search.data.mapper.meal.toDomain
import com.example.job_search.data.service.meal.MealService
import com.example.job_search.domein.model.meal.GetMeal
import com.example.job_search.domein.repository.meal.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealService: MealService,
    private val handleMealResponse: HandleMealResponse
): MealRepository {
    override suspend fun getSingleRandomMeal(): Flow<Resource<List<GetMeal>>> {
        return handleMealResponse.safeApiCall {
            mealService.getSingleRandomMeal()
        }.asResource {
            it.meals.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getMealByName(name: String): Flow<Resource<List<GetMeal>>> {
        return handleMealResponse.safeApiCall {
            mealService.getMealByName(name = name)
        }.asResource {
            it.meals.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getMealByIngredients(ingredient: String): Flow<Resource<List<GetMeal>>> {
        return handleMealResponse.safeApiCall {
            mealService.getMealByIngredients(ingredient = ingredient)
        }.asResource {
            it.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getMealById(id: String): Flow<Resource<List<GetMeal>>> {
        return handleMealResponse.safeApiCall {
            mealService.getMealById(id = id)
        }.asResource {
            it.map {
                it.toDomain()
            }
        }
    }
}

