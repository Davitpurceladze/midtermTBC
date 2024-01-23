package com.example.job_search.presentation.mapper.meal

import com.example.job_search.domein.model.meal.GetMeal
import com.example.job_search.presentation.model.Meal

fun GetMeal.toPresenter() =
    Meal(
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
