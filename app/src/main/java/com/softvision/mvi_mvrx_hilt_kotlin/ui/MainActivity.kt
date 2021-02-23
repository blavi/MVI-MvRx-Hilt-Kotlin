package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.mvi_mvrx_hilt_kotlin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_explorer, R.id.navigation_movies,
                R.id.navigation_search, R.id.navigation_tv_shows,
                R.id.navigation_favorites
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun goToDetails(item: TMDBItemDetails) {
        navController.currentDestination?.let { currentDestination ->
            var action: NavDirections? = null

            when (currentDestination.id) {
                R.id.navigation_explorer -> {
                    action = ExplorerFragmentDirections.actionNavigationExplorerToDetailsFragment(item)
                }

                R.id.navigation_movies -> {
                    action = MoviesFragmentDirections.actionNavigationMoviesToDetailsFragment(item)
                }

                R.id.navigation_tv_shows -> {
                    action = TVShowsFragmentDirections.actionNavigationTvShowsToDetailsFragment(item)
                }
            }

            action?.let {
                navController.navigate(it)
            }
        }
    }

}