package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.Genre
import com.softvision.domain.model.base.ItemDetails

data class MoviesByGenreState(
    val genresRequest: Async<List<Genre>> = Uninitialized,
    val genres: List<Genre> = emptyList(),

    val displayedGenre: Genre? = null,

    val moviesByGenreRequest: Async<List<ItemDetails>> = Uninitialized,
    val moviesByGenreList: List<ItemDetails> = emptyList(),

    val selectedItem: ItemDetails? = null
): MvRxState {

    fun combineMoviesByGenre(offset: Int, displayedGenreId: Int, newRequestItems: Async<List<ItemDetails>>): List<ItemDetails> {

        return (when {
            offset != 0 && this.displayedGenre?.id == displayedGenreId -> this.moviesByGenreList
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
    }
}

