package com.example.job_search.presentation.mapper.meal

import com.example.job_search.domein.model.meal.GetMealByIngredients
import com.example.job_search.presentation.model.MealByIngredients

fun GetMealByIngredients.toPresenter() = MealByIngredients(
    strMeal, strMealThumb, idMeal
)