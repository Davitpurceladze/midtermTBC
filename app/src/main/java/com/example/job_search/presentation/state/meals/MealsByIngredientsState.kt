package com.example.job_search.presentation.state.meals

import com.example.job_search.presentation.model.Meal
import com.example.job_search.presentation.model.MealByIngredients

data class MealsByIngredientsState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val meal: List<MealByIngredients>? = null
)