package com.example.job_search.presentation.screen.mealsByIngredients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_search.data.common.Resource
import com.example.job_search.domein.usecase.meal.GetMealByIngredients
import com.example.job_search.presentation.event.mealByIngredients.MealByIngredientsEvent
import com.example.job_search.presentation.mapper.meal.toPresenter
import com.example.job_search.presentation.state.meals.MealsByIngredientsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsByIngredientsViewModel @Inject constructor(
    private val getMealByIngredientsUseCase: GetMealByIngredients
) :ViewModel() {

    private val _mealByIngredientState = MutableStateFlow(MealsByIngredientsState())
    val mealByIngredientsState: StateFlow<MealsByIngredientsState> = _mealByIngredientState.asStateFlow()


    fun onEvent(event: MealByIngredientsEvent) {
        when( event) {
            is MealByIngredientsEvent.FetchMealByIngredient -> fetchMealByIngredient(ingredient = event.ingredient)
        }
    }

    private fun fetchMealByIngredient(ingredient: String) {
        viewModelScope.launch {
            getMealByIngredientsUseCase(ingredient).collect{
                when (it) {
                    is Resource.Loading -> _mealByIngredientState.update { currentState ->
                        currentState.copy(
                            isLoading = it.isLoading
                        )
                    }

                    is Resource.Failure -> _mealByIngredientState.update { currentState ->
                        currentState.copy(
                            errorMessage = it.errorMessage
                        )
                    }

                    is Resource.Success ->  {
                        _mealByIngredientState.update {currentState ->
                        currentState.copy(
                            meal = it.result.map {
                                it.toPresenter()
                            }
                        )

                        }
                    }
                }
            }
        }
    }


}