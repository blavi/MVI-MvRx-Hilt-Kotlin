package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
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

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_explorer, R.id.navigation_movies,
                R.id.navigation_search, R.id.navigation_tv_shows,
                R.id.navigation_favorites
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun goToDetails(item: TMDBItemDetails) {
        val bundle = bundleOf(DetailsFragment.ITEM to item)
        navController.navigate(R.id.action_navigation_explorer_to_popupDialogFragment, bundle)
    }

}