package com.example.job_search.presentation.screen.mealDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_search.data.common.Resource
import com.example.job_search.domein.usecase.meal.GetMealById
import com.example.job_search.presentation.event.mealDetails.MealDetailsEvent
import com.example.job_search.presentation.mapper.meal.toPresenter
import com.example.job_search.presentation.state.meals.MealsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val getMealByIdUseCase: GetMealById
) : ViewModel() {

    private val _mealDetailsState = MutableStateFlow(MealsState())
    val mealDetailsState: StateFlow<MealsState> = _mealDetailsState.asStateFlow()

    fun onEvent(event:MealDetailsEvent) {
        when( event) {
            is MealDetailsEvent.FetchMealById -> fetchMealById(id = event.id)
        }
    }

    private fun fetchMealById(id: String) {
        viewModelScope.launch {
            getMealByIdUseCase(id).collect{
                when (it) {
                    is Resource.Loading -> _mealDetailsState.update { currentState ->
                        currentState.copy(
                            isLoading = it.isLoading
                        )
                    }

                    is Resource.Failure -> _mealDetailsState.update { currentState ->
                        currentState.copy(
                            errorMessage = it.errorMessage
                        )
                    }

                    is Resource.Success -> _mealDetailsState.update { currentState ->
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