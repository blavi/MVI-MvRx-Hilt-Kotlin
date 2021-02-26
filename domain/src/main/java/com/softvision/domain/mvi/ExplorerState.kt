package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.TMDBItemDetails

data class ExplorerState(
    val trendingMoviesRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val trendingMovies: List<TMDBItemDetails> = emptyList(),

    val trendingTVShowsRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val trendingTVShows: List<TMDBItemDetails> = emptyList(),

    val popularMoviesRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val popularMovies: List<TMDBItemDetails> = emptyList(),

    val popularTVShowsRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val popularTVShows: List<TMDBItemDetails> = emptyList(),

    val comingSoonMoviesRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val comingSoonMovies: List<TMDBItemDetails> = emptyList(),

    val comingSoonTVShowsRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val comingSoonTVShows: List<TMDBItemDetails> = emptyList(),

    val selectedItem: TMDBItemDetails? = null
): MvRxState {

    fun combineTrendingMoviesItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.trendingMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineTrendingTVShowsItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.trendingTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularMoviesItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.popularMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularTVShowsItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.popularTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonMoviesItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.comingSoonMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonTVShowsItems(offset: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> =
        (when {
            offset != 0 -> this.comingSoonTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
}