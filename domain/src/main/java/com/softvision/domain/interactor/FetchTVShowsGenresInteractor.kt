package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.di.TvShowGenresRepository
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.GenresRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchTVShowsGenresInteractor @Inject constructor(@TvShowGenresRepository private val repository: GenresRepository<BaseItemDetails>):
    BaseFetchGenresUseCase<BaseItemDetails> {

    override fun invoke(): Single<List<BaseItemDetails>> {
        return repository.getData()
    }
}