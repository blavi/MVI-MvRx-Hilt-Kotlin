package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class FetchMoviesInteractor @Inject constructor(private val repository: ItemsRepository<String, TMDBMovieDetails, Int>): BaseFetchItemsUseCase<String, TMDBMovieDetails, Int> {

    override fun invoke(type: String, page: Int): Single<List<TMDBMovieDetails>> {
        Timber.i("Explore State: movies interactor")
        return repository.getData(type, page)
    }
}