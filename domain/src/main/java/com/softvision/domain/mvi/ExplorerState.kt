package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.base.ItemDetails

data class ExplorerState(
    val trendingMoviesRequest: Async<List<ItemDetails>> = Uninitialized,
    val trendingMovies: List<ItemDetails> = emptyList(),

    val trendingTVShowsRequest: Async<List<ItemDetails>> = Uninitialized,
    val trendingTVShows: List<ItemDetails> = emptyList(),

    val popularMoviesRequest: Async<List<ItemDetails>> = Uninitialized,
    val popularMovies: List<ItemDetails> = emptyList(),

    val popularTVShowsRequest: Async<List<ItemDetails>> = Uninitialized,
    val popularTVShows: List<ItemDetails> = emptyList(),

    val comingSoonMoviesRequest: Async<List<ItemDetails>> = Uninitialized,
    val comingSoonMovies: List<ItemDetails> = emptyList(),

    val comingSoonTVShowsRequest: Async<List<ItemDetails>> = Uninitialized,
    val comingSoonTVShows: List<ItemDetails> = emptyList(),

    val selectedItem: ItemDetails? = null
): MvRxState {

    fun combineTrendingMoviesItems(offset: Int, newRequestItems: Async<List<ItemDetails>>): List<ItemDetails> =
        (when {
            offset != 0 -> this.trendingMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineTrendingTVShowsItems(offset: Int, newRequestItems: Async<List<ItemDetails>>): List<ItemDetails> =
        (when {
            offset != 0 -> this.trendingTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularMoviesItems(offset: Int, newRequestItems: Async<List<ItemDetails>>): List<ItemDetails> =
        (when {
            offset != 0 -> this.popularMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combinePopularTVShowsItems(offset: Int, newRequestItems: Async<List<ItemDetails>>): List<ItemDetails> =
        (when {
            offset != 0 -> this.popularTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonMoviesItems(offset: Int, newRequestItems: Async<List<ItemDetails>>): List<ItemDetails> =
        (when {
            offset != 0 -> this.comingSoonMovies
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()

    fun combineComingSoonTVShowsItems(offset: Int, newRequestItems: Async<List<ItemDetails>>): List<ItemDetails> =
        (when {
            offset != 0 -> this.comingSoonTVShows
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
}