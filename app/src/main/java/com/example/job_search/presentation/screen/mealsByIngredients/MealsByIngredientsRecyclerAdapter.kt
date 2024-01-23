package com.example.job_search.presentation.screen.mealsByIngredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.job_search.databinding.ItemMealByNameLayoutBinding
import com.example.job_search.presentation.extension.loadImage
import com.example.job_search.presentation.model.Meal
import com.example.job_search.presentation.model.MealByIngredients

class MealsByIngredientsRecyclerAdapter: ListAdapter<MealByIngredients, MealsByIngredientsRecyclerAdapter.MealViewHolder>(MealsByIngredientDiffUtil()) {

    private var onMealDetailsClickListener: ((id: String) -> Unit)? = null

    fun setOnItemClickListener(listener: (id: String) -> Unit)  {
        onMealDetailsClickListener = listener
    }

    inner class MealViewHolder(private val binding: ItemMealByNameLayoutBinding): RecyclerView.ViewHolder(binding.root){
        private lateinit var item: MealByIngredients

        fun bind() {
            item = currentList[adapterPosition]
            binding.apply {
                imageMeal.loadImage(item.strMealThumb)
                tvMealName.text = item.strMeal
            }
            binding.btnNavigateToMealDetailFragment.setOnClickListener {
                onMealDetailsClickListener?.invoke(item.idMeal)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MealViewHolder(
        ItemMealByNameLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind()
    }

    class MealsByIngredientDiffUtil : DiffUtil.ItemCallback<MealByIngredients>() {
        override fun areItemsTheSame(oldItem: MealByIngredients, newItem: MealByIngredients): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealByIngredients, newItem: MealByIngredients): Boolean {
            return oldItem == newItem
        }
    }
}