package com.example.job_search.domein.usecase.meal

import com.example.job_search.domein.repository.meal.MealRepository
import javax.inject.Inject

class GetMealByName @Inject constructor(private val mealRepository: MealRepository) {
    suspend operator fun invoke(name:String) = mealRepository.getMealByName(name = name)
}