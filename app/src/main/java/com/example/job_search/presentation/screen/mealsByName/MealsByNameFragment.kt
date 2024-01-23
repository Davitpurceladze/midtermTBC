package com.example.job_search.presentation.screen.mealsByName

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.job_search.databinding.FragmentMealsByNameBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.mealByName.MealByNameEvent
import com.example.job_search.presentation.state.meals.MealsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealsByNameFragment : BaseFragment<FragmentMealsByNameBinding>(FragmentMealsByNameBinding::inflate) {

    private val viewModel: MealsByNameViewModel by viewModels()
    private  val mealsByNameRecyclerAdapter: MealsByNameRecyclerAdapter by lazy { MealsByNameRecyclerAdapter() }

    override fun bind() {
    }

    override fun bindViewActionListeners() {
        binding.imageSearch.setOnClickListener {
            val mealName = binding.etSearchByMealName.text.toString()
            if(mealName.length > 2) {
                fetchMealByName(mealName)
            }
        }

        mealsByNameRecyclerAdapter.setOnItemClickListener {
            findNavController().navigate(MealsByNameFragmentDirections.actionMealsByNameFragmentToMealDetailsFragment(id = it))
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mealByNameState.collect{
                    handleMealByNameState(it)
                }
            }
        }
    }

    private fun handleMealByNameState(state: MealsState) {
        state.meal?.let {
            createRecycler()
            mealsByNameRecyclerAdapter.submitList(it)

        }
    }

    private fun createRecycler() {
        binding.apply {
            recyclerMealsByName.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerMealsByName.adapter = mealsByNameRecyclerAdapter
        }
    }

    private fun fetchMealByName(name: String) {
        viewModel.onEvent(
            MealByNameEvent.FetchMealByName(name)
        )
    }
}