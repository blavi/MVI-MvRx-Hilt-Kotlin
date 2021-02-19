package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import android.util.Log
import com.airbnb.mvrx.*
import com.softvision.data.network.base.DataType
import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.interactor.FetchMoviesInteractor
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBMoviesByGenre
import com.softvision.domain.mvi.ExplorerState
import com.softvision.domain.mvi.MoviesByGenreState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.ExplorerFragment
import com.softvision.mvi_mvrx_hilt_kotlin.ui.MoviesFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Observable
import io.reactivex.Single

class MoviesViewModel @AssistedInject constructor(@Assisted initialExplorerState: MoviesByGenreState,
                                                  private val moviesInteractor: BaseFetchItemsUseCase<String, TMDBMovieDetails, Int>,
                                                  private val genresInteractor: BaseFetchGenresUseCase<TMDBGenre>
) :BaseMvRxViewModel<MoviesByGenreState>(initialExplorerState) {

    fun initiateGenresRequest() {
        setState {
            copy(genresRequest = Loading())
        }

        fetchGenres()
    }

    private fun fetchGenres() {
        Log.i("Explore State", "movies invoke")
        genresInteractor.invoke()
            .execute {
                copy(
                    genresRequest = it,
                    genres = it.invoke() ?: emptyList()
                )
            }
    }

    fun fetchMoviesByGenre(genreID: Int, offset: Int = 0) {
        moviesInteractor.invoke(genreID.toString(), offset / 20 + 1)
            .execute {
                copy(
                    moviesByGenreRequest = it,
                    moviesByGenreList = combineMoviesByGenre(offset, genreID, it),
                    displayedGenreId = genreID
                )
            }
    }

    fun loadMoreMoviesWithSelectedGenre() = withState {
        it.apply {
            if (moviesByGenreRequest.complete && moviesByGenreList.isNotEmpty() && displayedGenreId != null) {
                fetchMoviesByGenre(displayedGenreId!!, moviesByGenreList.count())
            }
        }
    }

    fun setSelectedItem(item: TMDBItemDetails?) {
        Log.i("Explore State", "item selected")
        setState {
            copy(selectedItem = item)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialExplorerState: MoviesByGenreState): MoviesViewModel
    }

    companion object : MvRxViewModelFactory<MoviesViewModel, MoviesByGenreState> {
        override fun create(viewModelContext: ViewModelContext, state: MoviesByGenreState): MoviesViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<MoviesFragment>()
                .viewModelFactory.create(state)
    }
}