package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.di.MovieGenresRepository
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.GenresRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchMoviesGenresInteractor @Inject constructor(@MovieGenresRepository private val repository: GenresRepository<BaseItemDetails>): BaseFetchGenresUseCase<BaseItemDetails> {

    override fun invoke(): Single<List<BaseItemDetails>> {
        return repository.getData()
    }
}