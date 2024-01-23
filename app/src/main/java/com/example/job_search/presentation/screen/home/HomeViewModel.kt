package com.example.job_search.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_search.data.common.Resource
import com.example.job_search.domein.usecase.meal.GetSingleRandomMealUseCase
import com.example.job_search.presentation.event.home.HomeEvents
import com.example.job_search.presentation.mapper.meal.toPresenter
import com.example.job_search.presentation.state.home.MealsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSingleRandomMealUseCase: GetSingleRandomMealUseCase
) : ViewModel() {

    private val _mealsState = MutableStateFlow(MealsState())
    val mealsState: StateFlow<MealsState> = _mealsState.asStateFlow()

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.FetchMeal -> fetchMeal()
        }
    }


    private fun fetchMeal() {
        viewModelScope.launch {
            getSingleRandomMealUseCase().collect {
                when (it) {
                    is Resource.Loading -> _mealsState.update { currentState ->
                        currentState.copy(
                            isLoading = it.isLoading
                        )
                    }

                    is Resource.Failure -> _mealsState.update { currentState ->
                        currentState.copy(
                            errorMessage = it.errorMessage
                        )
                    }

                    is Resource.Success -> _mealsState.update { currentState ->
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