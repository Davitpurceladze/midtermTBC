package com.example.job_search.domein.usecase.meal

import com.example.job_search.domein.repository.meal.MealRepository
import javax.inject.Inject

class GetMealById @Inject constructor(private val mealRepository: MealRepository) {
    suspend operator fun invoke(id: String) = mealRepository.getMealById(id = id)
}