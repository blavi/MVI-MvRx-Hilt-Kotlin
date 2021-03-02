package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import com.airbnb.mvrx.*
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.QueryInteractor
import com.softvision.domain.model.base.ItemDetails
import com.softvision.domain.mvi.SearchState
import com.softvision.mvi_mvrx_hilt_kotlin.ui.SearchFragment
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber

class SearchViewModel @AssistedInject constructor(@Assisted initialState: SearchState,
                                                  @QueryInteractor var queryInteractor: BaseFetchItemsUseCase<String, ItemDetails, Int>
): BaseMvRxViewModel<SearchState>(initialState) {

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
        }

        queryInteractor.invoke(query, offset / 20 + 1)
            .execute {
                copy(
                    searchRequest = it,
                    items = it.invoke() ?: emptyList()
                )
            }
    }
}