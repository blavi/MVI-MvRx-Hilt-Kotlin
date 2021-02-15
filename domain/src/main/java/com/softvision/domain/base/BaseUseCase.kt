package com.softvision.domain.base

import io.reactivex.Single


interface BaseUseCase<T: Any, R: Any, P: Any> {

  operator fun invoke(type: T, page: P): Single<List<R>>
}