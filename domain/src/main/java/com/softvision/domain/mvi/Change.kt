package com.softvision.domain.mvi

import com.softvision.domain.model.TMDBMovieDetails

sealed class Change {
    object Load : Change() {
        data class Success(val data: List<TMDBMovieDetails>) : Change()
        data class Failure(val error: Throwable) : Change()
    }
    object Clear : Change()
}
