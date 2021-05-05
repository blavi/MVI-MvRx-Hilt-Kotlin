package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.TvShowsByGenreRepository
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchTVShowsByGenreInteractor @Inject constructor(@TvShowsByGenreRepository private val repository: ItemsRepository<String, BaseItemDetails, Int>):
    BaseFetchItemsUseCase<String, BaseItemDetails, Int> {

    override fun invoke(type: String, page: Int): Single<List<BaseItemDetails>> {
        return repository.getData(type, page)
    }
}
