package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.GenreDetails

data class TVShowsByGenreState(
    val genresRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val genres: List<BaseItemDetails> = emptyList(),

    val displayedGenre: BaseItemDetails? = null,

    val tvShowsByGenreRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val tvShowsByGenreList: List<BaseItemDetails> = emptyList(),

    val selectedItem: BaseItemDetails? = null
): MavericksState {

    fun combineMoviesByGenre(offset: Int, displayedGenreId: Int, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> {

        return (when {
            offset != 0 && (this.displayedGenre as? GenreDetails)?.id == displayedGenreId -> this.tvShowsByGenreList
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
    }
}
