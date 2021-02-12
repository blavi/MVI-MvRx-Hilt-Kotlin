package com.softvision.domain.interactor

import com.softvision.domain.base.BaseUseCase
import com.softvision.domain.model.TMDBItemDetails
import io.reactivex.Observable
import io.reactivex.Single

interface  FetchInteractor: BaseUseCase<String, TMDBItemDetails, Int> {
     override operator fun invoke(type: String, page: Int): Single<List<TMDBItemDetails>>
}