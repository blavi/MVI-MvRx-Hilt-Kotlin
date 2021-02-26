package com.softvision.domain.mvi

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.softvision.domain.model.TMDBItemDetails

data class SearchState(
    val searchRequest: Async<List<TMDBItemDetails>> = Uninitialized,
    val items: List<TMDBItemDetails> = emptyList(),
): MvRxState
