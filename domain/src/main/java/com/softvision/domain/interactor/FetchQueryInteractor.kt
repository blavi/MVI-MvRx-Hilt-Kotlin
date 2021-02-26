package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.InteractorModule
import com.softvision.domain.di.QueryRepository
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class FetchQueryInteractor @Inject constructor(@QueryRepository private val repository: ItemsRepository<String, TMDBItemDetails, Int>):
    BaseFetchItemsUseCase<String, TMDBItemDetails, Int> {

    override fun invoke(type: String, page: Int): Single<List<TMDBItemDetails>> {
//        Timber.i("Explore State: movies interactor")
        return repository.getData(type, page)
    }
}