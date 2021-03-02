package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.TvShowsRepository
import com.softvision.domain.model.base.ItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class FetchTVShowsInteractor @Inject constructor(@TvShowsRepository private val repository: ItemsRepository<String, ItemDetails, Int>): BaseFetchItemsUseCase<String, ItemDetails, Int>  {

    override fun invoke(type: String, page: Int): Single<List<ItemDetails>> {
        Timber.i("Explore State: tv shows interactor")
        return repository.getData(type, page)
    }
}