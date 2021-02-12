package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import android.util.Log
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.softvision.data.network.base.DataType
import com.softvision.domain.interactor.FetchInteractor
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.mvi.ExplorerState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.ExplorerFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class ExplorerViewModel @AssistedInject constructor(@Assisted initialExplorerState: ExplorerState,
                                                    private val interactor: FetchInteractor)
    : BaseViewModel<ExplorerState>(initialExplorerState) {

    fun fetchTMDBItems() = withState {
        setState {
            copy(trendingRequest = Loading())
            copy(popularRequest = Loading())
            copy(comingSoonRequest = Loading())
        }

        fetchTrendingMovies()

        fetchPopularMovies()

        fetchComingSoonMovies()
    }


    private fun fetchPopularMovies(offset: Int = 0) {
        Log.i("Explore State", "popular invoke")
        interactor.invoke(DataType.POPULAR_MOVIES, offset / 20 + 1)
            .execute {
                copy(
                    popularRequest = it,
                    popularMovies = combinePopularMoviesItems(offset, it)
                )
            }
    }

    private fun fetchTrendingMovies(offset: Int = 0) {
        Log.i("Explore State", "trending invoke")
        interactor.invoke(DataType.TRENDING_MOVIES, offset / 20 + 1)
            .execute {
                copy(
                    trendingRequest = it,
                    trendingMovies = combineTrendingMoviesItems(offset, it)
                )
            }
    }

    private fun fetchComingSoonMovies(offset: Int = 0) {
        Log.i("Explore State", "coming soon invoke")
        interactor.invoke(DataType.COMING_SOON_MOVIES, offset / 20 + 1)
            .execute {
                copy(
                    comingSoonRequest = it,
                    comingSoonMovies = combineComingSoonMoviesItems(offset, it)
                )
            }
    }

    fun loadMorePopularMovies() = withState {
        it.popularMovies?.let { list ->
            if (it.popularRequest.complete && list.isNotEmpty()) {
                fetchPopularMovies(list.count())
            }
        }
    }

    fun loadMoreTrendingMovies() = withState {
        it.trendingMovies?.let { list ->
            if (it.trendingRequest.complete && list.isNotEmpty()) {
                fetchTrendingMovies(list.count())
            }
        }
    }

    fun loadMoreComingSoonMovies() = withState {
        it.comingSoonMovies?.let { list ->
            if (it.comingSoonRequest.complete && list.isNotEmpty()) {
                fetchComingSoonMovies(list.count())
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
        fun create(initialExplorerState: ExplorerState): ExplorerViewModel
    }

    companion object : MvRxViewModelFactory<ExplorerViewModel, ExplorerState> {
        override fun create(viewModelContext: ViewModelContext, initialExplorerState: ExplorerState): ExplorerViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<ExplorerFragment>()
                .viewModelFactory.create(initialExplorerState)
    }
}