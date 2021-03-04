package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.GenreDetails
import com.softvision.domain.model.BaseItemDetails

data class MoviesByGenreState(
    val genresRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val genres: List<BaseItemDetails> = emptyList(),

    val displayedGenre: BaseItemDetails? = null,

    val moviesByGenreRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val moviesByGenreList: List<BaseItemDetails> = emptyList(),

    val selectedItem: BaseItemDetails? = null
): MvRxState {

    fun combineMoviesByGenre(offset: Int, displayedGenreId: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> {

        return (when {
            offset != 0 && (this.displayedGenre as? GenreDetails)?.id == displayedGenreId -> this.moviesByGenreList
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
    }
}

