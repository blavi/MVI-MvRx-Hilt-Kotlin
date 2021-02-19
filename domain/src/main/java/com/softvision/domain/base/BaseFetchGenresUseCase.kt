package com.softvision.domain.base

import io.reactivex.Single

interface BaseFetchGenresUseCase<R: Any> {
    operator fun invoke(): Single<List<R>>
}