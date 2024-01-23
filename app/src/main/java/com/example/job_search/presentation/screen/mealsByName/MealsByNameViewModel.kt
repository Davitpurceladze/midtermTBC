package com.example.job_search.presentation.screen.mealsByName

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_search.data.common.Resource
import com.example.job_search.domein.usecase.meal.GetMealByName
import com.example.job_search.presentation.event.mealByName.MealByNameEvent
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
class MealsByNameViewModel @Inject constructor(
    private val getMealByNameUseCase: GetMealByName
) : ViewModel() {

    private val _mealByNameState = MutableStateFlow(MealsState())
    val mealByNameState: StateFlow<MealsState> = _mealByNameState.asStateFlow()

    fun onEvent(event: MealByNameEvent) {
        when (event) {
            is MealByNameEvent.fetchMealByName -> fetchMealByName(name = event.name)
        }
    }

    private fun fetchMealByName(name: String) {
        viewModelScope.launch {
            getMealByNameUseCase(name).collect {
                when (it) {
                    is Resource.Loading -> _mealByNameState.update { currentState ->
                        currentState.copy(
                            isLoading = it.isLoading
                        )
                    }

                    is Resource.Failure -> _mealByNameState.update { currentState ->
                        currentState.copy(
                            errorMessage = it.errorMessage
                        )
                    }

                    is Resource.Success -> _mealByNameState.update { currentState ->
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