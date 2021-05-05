package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import com.airbnb.mvrx.*
import com.softvision.data.network.base.MovieDataType
import com.softvision.data.network.base.TVShowDataType
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.MoviesInteractor
import com.softvision.domain.di.TvShowsInteractor
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.mvi.ExplorerState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.ExplorerFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ExplorerViewModel @AssistedInject constructor(@Assisted initialState: ExplorerState,
                                                    @MoviesInteractor var moviesInteractor: BaseFetchItemsUseCase<String, BaseItemDetails, Int>,
                                                    @TvShowsInteractor var tvShowsInteractor: BaseFetchItemsUseCase<String, BaseItemDetails, Int>)
    : BaseViewModel<ExplorerState>(initialState) {

    private var disposables: CompositeDisposable = CompositeDisposable()

    init {
        initiateLoadingMoviesAndTVShows()
    }

    private fun initiateLoadingMoviesAndTVShows() {
        setState {
            copy(trendingMoviesRequest = Loading())
            copy(trendingTVShowsRequest = Loading())

            copy(popularMoviesRequest = Loading())
            copy(popularTVShowsRequest = Loading())

            copy(comingSoonMoviesRequest = Loading())
            copy(comingSoonTVShowsRequest = Loading())
        }

        fetchTrendingMovies()
        fetchTrendingTVShows()

        fetchPopularMovies()
        fetchPopularTVShows()

        fetchComingSoonMovies()
        fetchComingSoonTVShows()
    }

    private fun fetchPopularMovies(offset: Int = 0) {
        val disposable = moviesInteractor.invoke(MovieDataType.POPULAR_MOVIES, offset / 20 + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    popularMoviesRequest = it,
                    popularMovies = combinePopularMoviesItems(offset, it)
                )
            }
        disposables.add(disposable)
    }

    private fun fetchPopularTVShows(offset: Int = 0) {
        val disposable = tvShowsInteractor.invoke(TVShowDataType.POPULAR_TV_SHOWS, offset / 20 + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    popularTVShowsRequest = it,
                    popularTVShows = combinePopularTVShowsItems(offset, it)
                )
            }
        disposables.add(disposable)
    }

    private fun fetchTrendingMovies(offset: Int = 0) {
        val disposable = moviesInteractor.invoke(MovieDataType.TRENDING_MOVIES, offset / 20 + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    trendingMoviesRequest = it,
                    trendingMovies = combineTrendingMoviesItems(offset, it)
                )
            }
        disposables.add(disposable)
    }

    private fun fetchTrendingTVShows(offset: Int = 0) {
        val disposable = tvShowsInteractor.invoke(TVShowDataType.TRENDING_TV_SHOWS, offset / 20 + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    trendingTVShowsRequest = it,
                    trendingTVShows = combineTrendingTVShowsItems(offset, it)
                )
            }
        disposables.add(disposable)
    }

    private fun fetchComingSoonMovies(offset: Int = 0) {
        val disposable = moviesInteractor.invoke(MovieDataType.COMING_SOON_MOVIES, offset / 20 + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    comingSoonMoviesRequest = it,
                    comingSoonMovies = combineComingSoonMoviesItems(offset, it)
                )
            }
        disposables.add(disposable)
    }

    private fun fetchComingSoonTVShows(offset: Int = 0) {
        val disposable = tvShowsInteractor.invoke(TVShowDataType.COMING_SOON_TV_SHOWS, offset / 20 + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    comingSoonTVShowsRequest = it,
                    comingSoonTVShows = combineComingSoonTVShowsItems(offset, it)
                )
            }
        disposables.add(disposable)
    }

    fun loadMorePopularMovies() = withState {
        it.apply {
            if (popularMoviesRequest.complete && popularMovies.isNotEmpty()) {
                fetchPopularMovies(popularMovies.count())
            }
        }
    }

    fun loadMoreTrendingMovies() = withState {
        it.apply{
            if (trendingMoviesRequest.complete && trendingMovies.isNotEmpty()) {
                fetchTrendingMovies(trendingMovies.count())
            }
        }
    }

    fun loadMoreComingSoonMovies() = withState {
        it.apply {
            if (comingSoonMoviesRequest.complete && comingSoonMovies.isNotEmpty()) {
                fetchComingSoonMovies(comingSoonMovies.count())
            }
        }
    }

    fun setSelectedItem(item: BaseItemDetails?) {
        setState {
            copy(selectedItem = item)
        }
    }

    fun loadMoreComingSoonTVShows() = withState {
        it.apply {
            if (comingSoonTVShowsRequest.complete && comingSoonTVShows.isNotEmpty()) {
                fetchComingSoonTVShows(comingSoonTVShows.count())
            }
        }
    }

    fun loadMorePopularTVShows() = withState {
        it.apply {
            if (popularTVShowsRequest.complete && popularTVShows.isNotEmpty()) {
                fetchPopularTVShows(popularTVShows.count())
            }
        }
    }

    fun loadMoreTrendingTVShows() = withState {
        it.apply{
            if (trendingTVShowsRequest.complete && trendingTVShows.isNotEmpty()) {
                fetchTrendingTVShows(trendingTVShows.count())
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: ExplorerState): ExplorerViewModel
    }

    companion object : MvRxViewModelFactory<ExplorerViewModel, ExplorerState> {
        override fun create(viewModelContext: ViewModelContext, state: ExplorerState): ExplorerViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<ExplorerFragment>()
                .viewModelFactory.create(state)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}