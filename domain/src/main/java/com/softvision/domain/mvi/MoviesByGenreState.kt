package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBItemDetails

data class MoviesByGenreState(
    val genresRequest: Async<List<TMDBGenre>> = Uninitialized,
    val genres: List<TMDBGenre> = emptyList(),

    val displayedGenre: TMDBGenre? = null,

    val moviesByGenreRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val moviesByGenreList: List<TMDBItemDetails> = emptyList(),

    val selectedItem: TMDBItemDetails? = null
): MvRxState {

    fun combineMoviesByGenre(offset: Int, displayedGenreId: Int, newRequestItems: Async<List<TMDBItemDetails>>): List<TMDBItemDetails> {

        return (when {
            offset != 0 && this.displayedGenre?.id == displayedGenreId -> this.moviesByGenreList
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
    }
}

