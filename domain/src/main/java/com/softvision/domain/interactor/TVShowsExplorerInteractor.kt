package com.softvision.domain.interactor

import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.repository.ResourcesRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class TVShowsExplorerInteractor @Inject constructor(private val repository: ResourcesRepository<String, TMDBTVShowDetails, Int>): FetchTVShowsInteractor{

    override fun invoke(type: String, page: Int): Single<List<TMDBTVShowDetails>> {
        Timber.i("Explore State: tv shows interactor")
        return repository.getData(type, page)
    }
}