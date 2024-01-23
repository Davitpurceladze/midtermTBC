package com.example.job_search.presentation.event.mealByName

sealed class MealByNameEvent {
    data class fetchMealByName(val name: String) : MealByNameEvent()
}