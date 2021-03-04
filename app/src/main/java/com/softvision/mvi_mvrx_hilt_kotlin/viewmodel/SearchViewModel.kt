package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import android.util.Log
import com.airbnb.mvrx.*
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.QueryInteractor
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.mvi.SearchState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.SearchFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchViewModel @AssistedInject constructor(@Assisted initialState: SearchState,
                                                  @QueryInteractor var queryInteractor: BaseFetchItemsUseCase<String, BaseItemDetails, Int>
): BaseMvRxViewModel<SearchState>(initialState) {

    private var disposables: CompositeDisposable = CompositeDisposable()

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: SearchState): SearchViewModel
    }

    companion object : MvRxViewModelFactory<SearchViewModel, SearchState> {
        override fun create(viewModelContext: ViewModelContext, state: SearchState): SearchViewModel =
            (viewModelContext as FragmentViewModelContext)
                .fragment<SearchFragment>()
                .viewModelFactory.create(state)
    }

    fun executeQuery(query: String, offset: Int = 0) {
        Timber.i("execute Query %s", query)
        setState {
            copy(searchRequest = Loading())
            copy(query = query)
        }

        val disposable = queryInteractor.invoke(query, offset / 20 + 1)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(
                    searchRequest = it,
                    items = combineItems(offset, query, it)
                )
            }
        disposables.add(disposable)
    }

    fun loadMoreItems() = withState {
        it.apply {
            if (searchRequest.complete && items.isNotEmpty() && query.isNotEmpty()) {
                executeQuery(query, items.count())
            }
        }
    }

    fun setSelectedItem(item: BaseItemDetails?) {
        Log.i("Search State", "item selected")
        setState {
            copy(selectedItem = item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}