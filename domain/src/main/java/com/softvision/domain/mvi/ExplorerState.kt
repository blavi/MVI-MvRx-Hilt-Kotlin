package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails

data class ExplorerState(
    val trendingMoviesRequest: Async<List<TMDBMovieDetails>> = Uninitialized,
    val trendingMovies: List<TMDBMovieDetails> = emptyList(),

    val trendingTVShowsRequest: Async<List<TMDBTVShowDetails>> = Uninitialized,
    val trendingTVShows: List<TMDBTVShowDetails> = emptyList(),

    val popularMoviesRequest: Async<List<TMDBMovieDetails>> = Uninitialized,
    val popularMovies: List<TMDBMovieDetails> = emptyList(),

    val popularTVShowsRequest: Async<List<TMDBTVShowDetails>> = Uninitialized,
    val popularTVShows: List<TMDBTVShowDetails> = emptyList(),

    val comingSoonMoviesRequest: Async<List<TMDBMovieDetails>> = Uninitialized,
    val comingSoonMovies: List<TMDBMovieDetails> = emptyList(),

    val comingSoonTVShowsRequest: Async<List<TMDBTVShowDetails>> = Uninitialized,
    val comingSoonTVShows: List<TMDBTVShowDetails> = emptyList(),

    val selectedItem: TMDBItemDetails? = null
): MvRxState {

    fun combineTrendingMoviesItems(offset: Int, newRequestItems: Async<List<TMDBMovieDetails>>): List<TMDBMovieDetails> =
        (when {
            offset != 0 -> this.trendingMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineTrendingTVShowsItems(offset: Int, newRequestItems: Async<List<TMDBTVShowDetails>>): List<TMDBTVShowDetails> =
        (when {
            offset != 0 -> this.trendingTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularMoviesItems(offset: Int, newRequestItems: Async<List<TMDBMovieDetails>>): List<TMDBMovieDetails> =
        (when {
            offset != 0 -> this.popularMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularTVShowsItems(offset: Int, newRequestItems: Async<List<TMDBTVShowDetails>>): List<TMDBTVShowDetails> =
        (when {
            offset != 0 -> this.popularTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonMoviesItems(offset: Int, newRequestItems: Async<List<TMDBMovieDetails>>): List<TMDBMovieDetails> =
        (when {
            offset != 0 -> this.comingSoonMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonTVShowsItems(offset: Int, newRequestItems: Async<List<TMDBTVShowDetails>>): List<TMDBTVShowDetails> =
        (when {
            offset != 0 -> this.comingSoonTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
}