package com.example.job_search.presentation.event.mealByName

sealed class MealByNameEvent {
    data class FetchMealByName(val name: String) : MealByNameEvent()
}