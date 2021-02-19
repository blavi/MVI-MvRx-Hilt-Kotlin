package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBMoviesByGenre

data class MoviesByGenreState(
    val genresRequest: Async<List<TMDBGenre>> = Uninitialized,
    val genres: List<TMDBGenre> = emptyList(),

    @PersistState val displayedGenreId: Int? = null,

    val moviesByGenreRequest: Async<List<TMDBMovieDetails>> = Uninitialized,
    val moviesByGenreList: List<TMDBMovieDetails> = emptyList(),

    val selectedItem: TMDBItemDetails? = null
): MvRxState {

    fun combineMoviesByGenre(offset: Int, displayedGenreId: Int, newRequestItems: Async<List<TMDBMovieDetails>>): List<TMDBMovieDetails> {

        return (when {
            offset != 0 && this.displayedGenreId == displayedGenreId -> this.moviesByGenreList
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
    }
}

