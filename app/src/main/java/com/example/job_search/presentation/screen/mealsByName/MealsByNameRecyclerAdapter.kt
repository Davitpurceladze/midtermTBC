package com.example.job_search.presentation.screen.mealsByName

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.job_search.databinding.FragmentMealsByNameBinding
import com.example.job_search.databinding.ItemMealByNameLayoutBinding
import com.example.job_search.presentation.extension.loadImage
import com.example.job_search.presentation.model.Meal

class MealsByNameRecyclerAdapter: ListAdapter<Meal, MealsByNameRecyclerAdapter.MealViewHolder>(MealsDiffUtil()) {
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

    inner class MealViewHolder(private val binding: ItemMealByNameLayoutBinding ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: Meal

        fun bind() {
            item = currentList[adapterPosition]
            binding.apply {
                imageMeal.loadImage(item.strMealThumb)
                tvMealName.text = item.strMeal
            }
        }
    }

    class MealsDiffUtil : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
}