package com.example.job_search.presentation.event.mealByIngredients

sealed class MealByIngredientsEvent {
    data class FetchMealByIngredient(val ingredient: String) :MealByIngredientsEvent()
}