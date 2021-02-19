package com.softvision.domain.repository

import io.reactivex.Single

interface GenresRepository<R: Any> {
    fun getData(): Single<List<R>>
}