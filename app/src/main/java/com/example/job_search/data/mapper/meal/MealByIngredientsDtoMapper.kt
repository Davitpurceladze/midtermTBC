package com.example.job_search.data.mapper.meal

import com.example.job_search.data.model.meal.MealByIngredientsDto
import com.example.job_search.domein.model.meal.GetMealByIngredients

fun MealByIngredientsDto.toDomain() = GetMealByIngredients(
    strMeal, strMealThumb, idMeal
)