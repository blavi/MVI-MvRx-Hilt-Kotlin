package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.BaseItemDetails

data class ExplorerState(
    val trendingMoviesRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val trendingMovies: List<BaseItemDetails> = emptyList(),

    val trendingTVShowsRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val trendingTVShows: List<BaseItemDetails> = emptyList(),

    val popularMoviesRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val popularMovies: List<BaseItemDetails> = emptyList(),

    val popularTVShowsRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val popularTVShows: List<BaseItemDetails> = emptyList(),

    val comingSoonMoviesRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val comingSoonMovies: List<BaseItemDetails> = emptyList(),

    val comingSoonTVShowsRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val comingSoonTVShows: List<BaseItemDetails> = emptyList(),

    val selectedItem: BaseItemDetails? = null
): MavericksState {

    fun combineTrendingMoviesItems(offset: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> =
        (when {
            offset != 0 -> this.trendingMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineTrendingTVShowsItems(offset: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> =
        (when {
            offset != 0 -> this.trendingTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularMoviesItems(offset: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> =
        (when {
            offset != 0 -> this.popularMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularTVShowsItems(offset: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> =
        (when {
            offset != 0 -> this.popularTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonMoviesItems(offset: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> =
        (when {
            offset != 0 -> this.comingSoonMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonTVShowsItems(offset: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> =
        (when {
            offset != 0 -> this.comingSoonTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
}