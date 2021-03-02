package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.MoviesRepository
import com.softvision.domain.model.base.ItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchMoviesInteractor @Inject constructor(@MoviesRepository private val repository: ItemsRepository<String, ItemDetails, Int>): BaseFetchItemsUseCase<String, ItemDetails, Int> {

    override fun invoke(type: String, page: Int): Single<List<ItemDetails>> {
//        Timber.i("Explore State: movies interactor")
        return repository.getData(type, page)
    }
}