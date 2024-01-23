package com.example.job_search.presentation.screen.home

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.job_search.databinding.FragmentHomeBinding
import com.example.job_search.presentation.base.BaseFragment
import com.example.job_search.presentation.event.home.HomeEvents
import com.example.job_search.presentation.extension.loadImage
import com.example.job_search.presentation.state.meals.MealsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var youtubeLink: String
    private var sourceLink: String? = null

    override fun bind() {
        binding.btnOpenYoutube.setOnClickListener {
            openYouTube()
        }

        binding.btnOpenSource.setOnClickListener {
            openSource()
        }
    }

    override fun bindViewActionListeners() {
        binding.btnFetchRandomMeal.setOnClickListener {
            fetchRandomMeal()
            showButtons()
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
            youtubeLink= it[0].strYoutube
            sourceLink= it[0].strSource
        }

    }

    private fun showButtons() {
        with(binding){
            btnOpenYoutube.visibility = View.VISIBLE
            btnOpenSource.visibility = View.VISIBLE
        }
    }

    private fun fetchRandomMeal() {
        viewModel.onEvent(
            HomeEvents.FetchMeal
        )
    }

    private fun openSource() {
        sourceLink?.let {
            openUrl(sourceLink!!)
        }
    }
    private fun openYouTube() {
        openUrl(youtubeLink)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}