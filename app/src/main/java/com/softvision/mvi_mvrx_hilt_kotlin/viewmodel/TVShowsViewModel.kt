package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import com.airbnb.mvrx.*
import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.TvShowGenresInteractor
import com.softvision.domain.di.TvShowsByGenreInteractor
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.GenreDetails
import com.softvision.domain.mvi.TVShowsByGenreState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.TVShowsFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TVShowsViewModel @AssistedInject constructor(
    @Assisted initialState: TVShowsByGenreState,
    @TvShowsByGenreInteractor private val tvShowsInteractor: BaseFetchItemsUseCase<String, BaseItemDetails, Int>,
    @TvShowGenresInteractor private val genresInteractor: BaseFetchGenresUseCase<BaseItemDetails>
) : BaseViewModel<TVShowsByGenreState>(initialState) {

    private var disposables: CompositeDisposable = CompositeDisposable()

    init {
        loadGenresAndTvShows()
    }

    private fun loadGenresAndTvShows() {
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

    fun fetchTvShowsByGenre(offset: Int = 0) = withState { state ->
        (state.displayedGenre as? GenreDetails)?.id?.let { id ->
            Timber.i("Explore State: genre id: %s", id)
            val moviesByGenreDisposable = tvShowsInteractor.invoke(id.toString(), offset / 20 + 1)
                .subscribeOn(Schedulers.io())
                .execute {
                    copy(
                        tvShowsByGenreRequest = it,
                        tvShowsByGenreList = combineMoviesByGenre(offset, id, it)
                    )
                }
            disposables.add(moviesByGenreDisposable)
        }

        Timber.i("Movies: %s", this)
    }

    fun loadMoreMoviesWithSelectedGenre() = withState {
        it.apply {
            if (tvShowsByGenreRequest.complete && tvShowsByGenreList.isNotEmpty() && displayedGenre != null) {
                fetchTvShowsByGenre(tvShowsByGenreList.count())
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
        fun create(initialState: TVShowsByGenreState): TVShowsViewModel
    }

    companion object : MavericksViewModelFactory<TVShowsViewModel, TVShowsByGenreState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: TVShowsByGenreState
        ): TVShowsViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<TVShowsFragment>()
                .viewModelFactory.create(state)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}