package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import com.airbnb.mvrx.*
import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.MovieGenresInteractor
import com.softvision.domain.di.MoviesByGenreInteractor
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.GenreDetails
import com.softvision.domain.mvi.MoviesByGenreState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.MoviesFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MoviesViewModel @AssistedInject constructor(
    @Assisted initialState: MoviesByGenreState,
    @MoviesByGenreInteractor private val moviesInteractor: BaseFetchItemsUseCase<String, BaseItemDetails, Int>,
    @MovieGenresInteractor private val genresInteractor: BaseFetchGenresUseCase<BaseItemDetails>
) : BaseViewModel<MoviesByGenreState>(initialState) {

    private var disposables: CompositeDisposable = CompositeDisposable()

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
        val genresDisposable = genresInteractor.invoke()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    genresRequest = it,
                    genres = it.invoke() ?: emptyList(),
                    displayedGenre = it.invoke()?.get(0)
                )
            }
        disposables.add(genresDisposable)
    }

    fun fetchMoviesByGenre(offset: Int = 0) = withState { state ->
        (state.displayedGenre as? GenreDetails)?.id?.let { id ->
            Timber.i("Explore State: genre id: %s", id)
            val moviesByGenreDisposable = moviesInteractor.invoke(id.toString(), offset / 20 + 1)
                .subscribeOn(Schedulers.io())
                .execute {
                    copy(
                        moviesByGenreRequest = it,
                        moviesByGenreList = combineMoviesByGenre(offset, id, it)
                    )
                }
            disposables.add(moviesByGenreDisposable)
        }

        Timber.i("Movies: %s", this)
    }

    fun loadMoreMoviesWithSelectedGenre() = withState {
        it.apply {
            if (moviesByGenreRequest.complete && moviesByGenreList.isNotEmpty() && displayedGenre != null) {
                fetchMoviesByGenre(moviesByGenreList.count())
            }
        }
    }

    fun setSelectedItem(item: BaseItemDetails?) {
        setState {
            copy(selectedItem = item)
        }
    }

    fun updateSelectedGenre(genreDetails: GenreDetails) {
        Timber.i("Explore State: update selected genre id: %s", genreDetails.name)
        setState {
            copy(displayedGenre = genreDetails)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: MoviesByGenreState): MoviesViewModel
    }

    companion object : MvRxViewModelFactory<MoviesViewModel, MoviesByGenreState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: MoviesByGenreState
        ): MoviesViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<MoviesFragment>()
                .viewModelFactory.create(state)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}