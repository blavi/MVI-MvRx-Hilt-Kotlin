package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.model.Genre
import com.softvision.domain.repository.GenresRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchMoviesGenresInteractor @Inject constructor(private val repository: GenresRepository<Genre>): BaseFetchGenresUseCase<Genre> {

    override fun invoke(): Single<List<Genre>> {
//        Timber.i("Explore State: genres interactor")
        return repository.getData()
    }
}