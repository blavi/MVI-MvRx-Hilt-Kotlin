package com.softvision.domain.repository

import com.softvision.domain.model.TMDBItemDetails
import io.reactivex.Observable
import io.reactivex.Single

interface  ResourcesRepository<T: Any, R: Any, P: Any> {
    fun getData(type: T, page: P): Single<List<TMDBItemDetails>>
}