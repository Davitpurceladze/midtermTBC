package com.example.job_search.presentation.screen.mealDetails

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.job_search.databinding.FragmentMealDetailsBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.mealDetails.MealDetailsEvent
import com.example.job_search.presentation.extension.loadImage
import com.example.job_search.presentation.state.meals.MealsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailsFragment :
    BaseFragment<FragmentMealDetailsBinding>(FragmentMealDetailsBinding::inflate) {

    private val viewModel: MealDetailsViewModel by viewModels()
    private val args: MealDetailsFragmentArgs by navArgs()

    override fun bind() {
        viewModel.onEvent(
            MealDetailsEvent.FetchMealById(
                 id = args.id
            )
        )
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mealDetailsState.collect {
                    handleMealDetailsState(it)
                }
            }
        }
    }

    private fun handleMealDetailsState(state: MealsState) {
        state.meal?.let {
            with(binding) {
                imgMeal.loadImage(it[0].strMealThumb)
                tvMealCategory.text = it[0].strCategory
                tvMealArea.text = it[0].strArea
                tvMealInstruction.text = it[0].strInstructions
                tvMealName.text = it[0].strMeal

            }
        }
    }
}