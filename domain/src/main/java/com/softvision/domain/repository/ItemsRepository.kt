package com.softvision.domain.repository

import io.reactivex.Single

interface  ItemsRepository<T: Any, R: Any, P: Any> {
    fun getData(type: T, page: P): Single<List<R>>
}