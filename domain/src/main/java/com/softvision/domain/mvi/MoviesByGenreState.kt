package com.softvision.domain.mvi

import com.airbnb.mvrx.*
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails

data class MoviesByGenreState(
    val genresRequest: Async<List<TMDBGenre>> = Uninitialized,
    val genres: List<TMDBGenre> = emptyList(),

    val displayedGenre: TMDBGenre? = null,

    val moviesByGenreRequest: Async<List<TMDBMovieDetails>> = Uninitialized,
    val moviesByGenreList: List<TMDBMovieDetails> = emptyList(),

    val selectedItem: TMDBItemDetails? = null
): MvRxState {

    fun combineMoviesByGenre(offset: Int, displayedGenreId: Int, newRequestItems: Async<List<TMDBMovieDetails>>): List<TMDBMovieDetails> {

        return (when {
            offset != 0 && this.displayedGenre?.id == displayedGenreId -> this.moviesByGenreList
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
    }
}

