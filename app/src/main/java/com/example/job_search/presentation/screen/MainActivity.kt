package com.example.job_search.presentation.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.example.job_search.R
import com.example.job_search.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                    true
                }

                R.id.mealsByName -> {
                    if (navController.currentDestination?.id != R.id.mealsByName) {
                        navController.navigate(R.id.mealsByNameFragment)
                    }
                    true
                }

                R.id.mealsByIngredients -> {
                    if (navController.currentDestination?.id != R.id.mealsByIngredients) {
                        navController.navigate(R.id.mealsByIngredientsFragment)
                    }
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.registrationFragment || destination.id == R.id.logInFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
            with(binding.bottomNavigationView) {
                when (destination.id) {
                    R.id.homeFragment -> selectedItemId = R.id.homeFragment
                    R.id.mealsByName -> selectedItemId = R.id.mealsByName
                    R.id.mealsByIngredients -> selectedItemId = R.id.mealsByIngredients
                }
            }
        }
    }
}