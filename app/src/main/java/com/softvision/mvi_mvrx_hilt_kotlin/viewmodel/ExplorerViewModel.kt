package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import android.util.Log
import com.airbnb.mvrx.*
import com.softvision.data.network.base.DataType
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.mvi.ExplorerState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.ExplorerFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class ExplorerViewModel @AssistedInject constructor(@Assisted initialExplorerState: ExplorerState,
                                                    private val moviesInteractor: BaseFetchItemsUseCase<String, TMDBMovieDetails, Int>,
                                                    private val tvShowsInteractor: BaseFetchItemsUseCase<String, TMDBTVShowDetails, Int>)
    :BaseMvRxViewModel<ExplorerState>(initialExplorerState) {

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
        Log.i("Explore State", "popular invoke")
        moviesInteractor.invoke(DataType.POPULAR_MOVIES, offset / 20 + 1)
            .execute {
                copy(
                    popularMoviesRequest = it,
                    popularMovies = combinePopularMoviesItems(offset, it)
                )
            }
    }

    private fun fetchPopularTVShows(offset: Int = 0) {
        Log.i("Explore State", "popular tv shows invoke")
        tvShowsInteractor.invoke(DataType.POPULAR_TV_SHOWS, offset / 20 + 1)
            .execute {
                copy(
                    popularTVShowsRequest = it,
                    popularTVShows = combinePopularTVShowsItems(offset, it)
                )
            }
    }

    private fun fetchTrendingMovies(offset: Int = 0) {
        Log.i("Explore State", "trending invoke")
        moviesInteractor.invoke(DataType.TRENDING_MOVIES, offset / 20 + 1)
            .execute {
                copy(
                    trendingMoviesRequest = it,
                    trendingMovies = combineTrendingMoviesItems(offset, it)
                )
            }
    }

    private fun fetchTrendingTVShows(offset: Int = 0) {
        Log.i("Explore State", "trending invoke")
        tvShowsInteractor.invoke(DataType.TRENDING_TV_SHOWS, offset / 20 + 1)
            .execute {
                copy(
                    trendingTVShowsRequest = it,
                    trendingTVShows = combineTrendingTVShowsItems(offset, it)
                )
            }
    }

    private fun fetchComingSoonMovies(offset: Int = 0) {
        Log.i("Explore State", "coming soon invoke")
        moviesInteractor.invoke(DataType.COMING_SOON_MOVIES, offset / 20 + 1)
            .execute {
                copy(
                    comingSoonMoviesRequest = it,
                    comingSoonMovies = combineComingSoonMoviesItems(offset, it)
                )
            }
    }

    private fun fetchComingSoonTVShows(offset: Int = 0) {
        Log.i("Explore State", "coming soon invoke")
        tvShowsInteractor.invoke(DataType.COMING_SOON_TV_SHOWS, offset / 20 + 1)
            .execute {
                copy(
                    comingSoonTVShowsRequest = it,
                    comingSoonTVShows = combineComingSoonTVShowsItems(offset, it)
                )
            }
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

    fun setSelectedItem(item: TMDBItemDetails?) {
        Log.i("Explore State", "item selected")
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
        fun create(initialExplorerState: ExplorerState): ExplorerViewModel
    }

    companion object : MvRxViewModelFactory<ExplorerViewModel, ExplorerState> {
        override fun create(viewModelContext: ViewModelContext, state: ExplorerState): ExplorerViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<ExplorerFragment>()
                .viewModelFactory.create(state)
    }
}