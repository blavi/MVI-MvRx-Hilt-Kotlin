package com.softvision.domain.mvi

import com.softvision.domain.model.TMDBItemDetails

sealed class Change {
    object Load : Change() {
        data class Success(val data: List<TMDBItemDetails>) : Change()
        data class Failure(val error: Throwable) : Change()
    }
    object Clear : Change()
}
