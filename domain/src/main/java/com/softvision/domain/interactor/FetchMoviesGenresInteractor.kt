package com.softvision.domain.interactor

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.repository.GenresRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class FetchMoviesGenresInteractor @Inject constructor(private val repository: GenresRepository<TMDBGenre>): BaseFetchGenresUseCase<TMDBGenre> {

    override fun invoke(): Single<List<TMDBGenre>> {
        Timber.i("Explore State: genres interactor")
        return repository.getData()
    }
}