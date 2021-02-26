package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

//import com.softvision.mvi_mvrx_hilt_kotlin.di.AssistedViewModelFactory
import android.util.Log
import com.airbnb.mvrx.*
import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.InteractorModule
import com.softvision.domain.di.MoviesInteractor
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.mvi.MoviesByGenreState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.MoviesFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class MoviesViewModel @AssistedInject constructor(@Assisted initialState: MoviesByGenreState,
                                                  @MoviesInteractor private val moviesInteractor: BaseFetchItemsUseCase<String, TMDBItemDetails, Int>,
                                                  private val genresInteractor: BaseFetchGenresUseCase<TMDBGenre>
) :BaseMvRxViewModel<MoviesByGenreState>(initialState) {

    init {
        initiateLoadingGenresAndMovies()
    }

    private fun initiateLoadingGenresAndMovies() {
        setState {
            copy(genresRequest = Loading())
        }

        fetchGenres()
    }

    private fun fetchGenres() {
        Timber.i("Movies: movie genres invoke")
        genresInteractor.invoke()
            .execute {
                copy(
                    genresRequest = it,
                    genres = it.invoke() ?: emptyList(),
                    displayedGenre = it.invoke()?.get(0)
                )
            }
    }

    fun fetchMoviesByGenre(offset: Int = 0) = withState { state ->
        state.displayedGenre?.let { genre ->
            Timber.i("Explore State: genre id: %s", genre.id)
            moviesInteractor.invoke(genre.id.toString(), offset / 20 + 1)
                .execute {
                    copy(
                        moviesByGenreRequest = it,
                        moviesByGenreList = combineMoviesByGenre(offset, genre.id, it)
                    )
                }
        }

        Timber.i("Movies: %s",  this)
    }

    fun loadMoreMoviesWithSelectedGenre() = withState {
        it.apply {
            if (moviesByGenreRequest.complete && moviesByGenreList.isNotEmpty() && displayedGenre != null) {
                fetchMoviesByGenre(moviesByGenreList.count())
            }
        }
    }

    fun setSelectedItem(item: TMDBItemDetails?) {
        Log.i("Explore State", "item selected")
        setState {
            copy(selectedItem = item)
        }
    }

    fun updateSelectedGenre(genre: TMDBGenre) {
        Timber.i("Explore State: update selected genre id: %s", genre.name)
        setState {
            copy(displayedGenre = genre)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: MoviesByGenreState): MoviesViewModel
    }

    companion object : MvRxViewModelFactory<MoviesViewModel, MoviesByGenreState> {
        override fun create(viewModelContext: ViewModelContext, initialState: MoviesByGenreState): MoviesViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<MoviesFragment>()
                .viewModelFactory.create(initialState)
    }
}