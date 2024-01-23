package com.example.job_search.data.mapper.meal

import com.example.job_search.data.model.meal.MealDto
import com.example.job_search.domein.model.meal.GetMeal

fun MealDto.toDomain() = GetMeal(
    idMeal,
    strMeal,
    strArea,
    strCategory,
    strInstructions,
    strMealThumb,
    strYoutube,
    strIngredient1,
    strIngredient2,
    strIngredient3,
    strIngredient4,
    strIngredient5,
    strIngredient6,
    strIngredient7,
    strIngredient8,
    strIngredient9,
    strMeasure1,
    strMeasure2,
    strMeasure3,
    strMeasure4,
    strMeasure5,
    strMeasure6,
    strMeasure7,
    strMeasure8,
    strMeasure9,
    strSource
)

