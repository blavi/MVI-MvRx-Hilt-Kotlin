package com.softvision.domain.interactor

import com.softvision.domain.base.BaseUseCase
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import io.reactivex.Single

interface  FetchMoviesInteractor: BaseUseCase<String, TMDBMovieDetails, Int> {
     override operator fun invoke(type: String, page: Int): Single<List<TMDBMovieDetails>>
}