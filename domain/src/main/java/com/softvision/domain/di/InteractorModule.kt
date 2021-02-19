package com.softvision.domain.di

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.interactor.FetchMoviesGenresInteractor
import com.softvision.domain.interactor.FetchMoviesInteractor
import com.softvision.domain.interactor.FetchTVShowsInteractor
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class InteractorModule {

    @Binds
    abstract fun bindMoviesInteractor(impl: FetchMoviesInteractor): BaseFetchItemsUseCase<String, TMDBMovieDetails, Int>

    @Binds
    abstract fun bindTVShowsInteractor(impl: FetchTVShowsInteractor): BaseFetchItemsUseCase<String, TMDBTVShowDetails, Int>

    @Binds
    abstract fun bindGenresInteractor(impl: FetchMoviesGenresInteractor): BaseFetchGenresUseCase<TMDBGenre>
}