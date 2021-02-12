package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.TMDBItemDetails

data class ExplorerState(
    val trendingRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val trendingMovies: List<TMDBItemDetails> = emptyList(),

    val popularRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val popularMovies: List<TMDBItemDetails> = emptyList(),

    val comingSoonRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val comingSoonMovies: List<TMDBItemDetails> = emptyList(),

    val selectedItem: TMDBItemDetails? = null
) : MvRxState {

    fun combineTrendingMoviesItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.trendingMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularMoviesItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.popularMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()


    fun combineComingSoonMoviesItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.comingSoonMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
}