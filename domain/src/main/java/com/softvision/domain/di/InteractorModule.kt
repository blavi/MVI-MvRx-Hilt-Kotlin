package com.softvision.domain.di

import com.softvision.domain.base.BaseUseCase
import com.softvision.domain.interactor.FetchMoviesInteractor
import com.softvision.domain.interactor.FetchTVShowsInteractor
import com.softvision.domain.interactor.MoviesExplorerInteractor
import com.softvision.domain.interactor.TVShowsExplorerInteractor
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
abstract class InteractorModule {

    @Binds
    @Named("MoviesInteractor")
    abstract fun bindMoviesInteractor(impl: MoviesExplorerInteractor): FetchMoviesInteractor

    @Binds
    @Named("TVShowsInteractor")
    abstract fun bindTVShowsInteractor(impl: TVShowsExplorerInteractor): FetchTVShowsInteractor
}