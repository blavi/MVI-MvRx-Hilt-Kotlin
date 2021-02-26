package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.di.InteractorModule
import com.softvision.domain.di.TvShowsRepository
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class FetchTVShowsInteractor @Inject constructor(@TvShowsRepository private val repository: ItemsRepository<String, TMDBItemDetails, Int>): BaseFetchItemsUseCase<String, TMDBItemDetails, Int>  {

    override fun invoke(type: String, page: Int): Single<List<TMDBItemDetails>> {
        Timber.i("Explore State: tv shows interactor")
        return repository.getData(type, page)
    }
}