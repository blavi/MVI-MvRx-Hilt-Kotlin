package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.base.ItemDetails

data class SearchState(
    val searchRequest: Async<List<ItemDetails>> = Uninitialized,
    val items: List<ItemDetails> = emptyList(),
): MvRxState
