package com.example.job_search.presentation.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.job_search.databinding.FragmentHomeBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.home.HomeEvents
import com.example.job_search.presentation.extension.loadImage
import com.example.job_search.presentation.state.home.MealsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun bind() {

    }

    override fun bindViewActionListeners() {
        binding.btnFetchRandomMeal.setOnClickListener {
            fetchRandomMeal()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.mealsState.collect{
                    handleHomeState(it)
                }
            }
        }
    }

    private fun handleHomeState(state: MealsState) {
        state.meal?.let {
            with(binding){
                tvMealName.text = it[0].strMeal
                tvDescription.text = it[0].strInstructions
                imgMeal.loadImage(it[0].strMealThumb)
            }
        }

    }

    private fun fetchRandomMeal() {
        viewModel.onEvent(
            HomeEvents.FetchMeal
        )
    }
}