package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.BaseItemDetails

data class SearchState(
    val searchRequest: Async<List<BaseItemDetails>> = Uninitialized,
    val items: List<BaseItemDetails> = emptyList(),
    val query: String = "",
    val selectedItem: BaseItemDetails? = null
): MvRxState {
    fun combineItems(offset: Int, query: String, newRequestItems: Async<List<BaseItemDetails>>): List<BaseItemDetails> {

        return (when {
            offset != 0 && this.query == query -> this.items
            else -> emptyList()
        } + (newRequestItems() ?: emptyList()))
            .distinct()
    }
}
