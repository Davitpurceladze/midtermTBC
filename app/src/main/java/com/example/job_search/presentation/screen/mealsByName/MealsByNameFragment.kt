package com.example.job_search.presentation.screen.mealsByName

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.job_search.databinding.FragmentMealsByNameBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.mealByName.MealByNameEvent
import com.example.job_search.presentation.state.home.MealsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealsByNameFragment : BaseFragment<FragmentMealsByNameBinding>(FragmentMealsByNameBinding::inflate) {

    private val viewModel: MealsByNameViewModel by viewModels()
    private lateinit var mealsByNameRecyclerAdapter: MealsByNameRecyclerAdapter

    override fun bind() {
    }

    override fun bindViewActionListeners() {
        binding.imageSearch.setOnClickListener {
            val mealName = binding.etSearchByMealName.text.toString()
            if(mealName.length > 2) {
                fetchMealByName(mealName)

            }
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
//            here we should submit list for recycler
            createRecycler()
            mealsByNameRecyclerAdapter.submitList(it)

        }
    }

    private fun createRecycler() {
        mealsByNameRecyclerAdapter = MealsByNameRecyclerAdapter()
        binding.apply {
            recyclerMealsByName.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerMealsByName.adapter = mealsByNameRecyclerAdapter
        }
    }

    private fun fetchMealByName(name: String) {
        viewModel.onEvent(
            MealByNameEvent.fetchMealByName(name)
        )
    }
}