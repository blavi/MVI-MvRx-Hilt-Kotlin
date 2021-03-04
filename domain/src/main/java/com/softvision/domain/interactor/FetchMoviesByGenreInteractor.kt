package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.MoviesByGenreRepository
import com.softvision.domain.di.MoviesRepository
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchMoviesByGenreInteractor @Inject constructor(@MoviesByGenreRepository private val repository: ItemsRepository<String, BaseItemDetails, Int>):
    BaseFetchItemsUseCase<String, BaseItemDetails, Int> {

    override fun invoke(type: String, page: Int): Single<List<BaseItemDetails>> {
//        Timber.i("Explore State: movies interactor")
        return repository.getData(type, page)
    }
}