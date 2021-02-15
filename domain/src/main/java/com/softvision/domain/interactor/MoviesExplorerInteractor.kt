package com.softvision.domain.interactor

import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.repository.ResourcesRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class MoviesExplorerInteractor @Inject constructor(private val repository: ResourcesRepository<String, TMDBMovieDetails, Int>): FetchMoviesInteractor {

    override fun invoke(type: String, page: Int): Single<List<TMDBMovieDetails>> {
        Timber.i("Explore State: movies interactor")
        return repository.getData(type, page)
    }
}