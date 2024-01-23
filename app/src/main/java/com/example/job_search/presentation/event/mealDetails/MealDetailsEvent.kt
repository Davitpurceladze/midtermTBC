package com.example.job_search.presentation.event.mealDetails

sealed class MealDetailsEvent {
    data class FetchMealById(val id: String) :MealDetailsEvent()
}