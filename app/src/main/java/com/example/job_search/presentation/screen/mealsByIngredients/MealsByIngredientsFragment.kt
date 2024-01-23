package com.example.job_search.presentation.screen.mealsByIngredients

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.job_search.databinding.FragmentMealsByIngredientsBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.mealByIngredients.MealByIngredientsEvent
import com.example.job_search.presentation.state.meals.MealsByIngredientsState
import com.example.job_search.presentation.state.meals.MealsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealsByIngredientsFragment : BaseFragment<FragmentMealsByIngredientsBinding>(FragmentMealsByIngredientsBinding::inflate) {

    private val viewModel: MealsByIngredientsViewModel by viewModels()
    private val mealsByIngredientRecyclerAdapter: MealsByIngredientsRecyclerAdapter by lazy { MealsByIngredientsRecyclerAdapter() }

    override fun bind() {
    }

    override fun bindViewActionListeners() {
        binding.imageSearch.setOnClickListener {
            val ingredient = binding.etSearchByIngredient.text.toString()
            if(ingredient.length > 2) {
                fetchMealByIngredient(ingredient)
            }
        }
        mealsByIngredientRecyclerAdapter.setOnItemClickListener {
            findNavController().navigate(MealsByIngredientsFragmentDirections.actionMealsByIngredientsFragmentToMealDetailsFragment(it))
        }


    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mealByIngredientsState.collect{
                    handleMealByIngredientState(it)
                }
            }
        }
    }

    private fun handleMealByIngredientState(state: MealsByIngredientsState) {
        state.meal?.let {
            createRecycler()
            mealsByIngredientRecyclerAdapter.submitList(it)
        }
    }

    private fun createRecycler() {
        binding.apply {
            recyclerMealsByIngredient.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerMealsByIngredient.adapter = mealsByIngredientRecyclerAdapter
        }
    }

    private fun fetchMealByIngredient(ingredient: String) {
        viewModel.onEvent(
            MealByIngredientsEvent.FetchMealByIngredient(ingredient)
        )
    }
}