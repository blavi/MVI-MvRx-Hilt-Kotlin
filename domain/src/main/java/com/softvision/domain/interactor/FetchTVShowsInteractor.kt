package com.softvision.domain.interactor

import com.softvision.domain.base.BaseUseCase
import com.softvision.domain.model.TMDBTVShowDetails
import io.reactivex.Single

interface  FetchTVShowsInteractor: BaseUseCase<String, TMDBTVShowDetails, Int> {
    override operator fun invoke(type: String, page: Int): Single<List<TMDBTVShowDetails>>
}