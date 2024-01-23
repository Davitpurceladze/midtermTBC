package com.example.job_search.presentation.state.home

import com.example.job_search.presentation.model.Meal

data class MealsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val meal: List<Meal>? = null
)