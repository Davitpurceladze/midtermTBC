package com.example.job_search.presentation.screen.mealDetails

import android.content.Intent
import android.net.Uri
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
    private lateinit var youtubeLink: String
    private var sourceLink: String? = null

    override fun bind() {
        viewModel.onEvent(
            MealDetailsEvent.FetchMealById(
                id = args.id
            )
        )
    }

    override fun bindViewActionListeners() {
        binding.btnOpenYoutube.setOnClickListener {
            openYouTube()
        }

        binding.btnOpenSource.setOnClickListener {
            openSource()
        }

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
            youtubeLink = it[0].strYoutube
            sourceLink = it[0].strSource
        }
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