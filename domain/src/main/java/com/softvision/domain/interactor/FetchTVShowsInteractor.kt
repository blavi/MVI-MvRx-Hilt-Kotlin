package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class FetchTVShowsInteractor @Inject constructor(private val repository: ItemsRepository<String, TMDBTVShowDetails, Int>): BaseFetchItemsUseCase<String, TMDBTVShowDetails, Int>  {

    override fun invoke(type: String, page: Int): Single<List<TMDBTVShowDetails>> {
        Timber.i("Explore State: tv shows interactor")
        return repository.getData(type, page)
    }
}