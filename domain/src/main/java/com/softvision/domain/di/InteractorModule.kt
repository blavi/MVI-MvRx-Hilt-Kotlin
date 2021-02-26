package com.softvision.domain.di

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.interactor.FetchMoviesGenresInteractor
import com.softvision.domain.interactor.FetchMoviesInteractor
import com.softvision.domain.interactor.FetchQueryInteractor
import com.softvision.domain.interactor.FetchTVShowsInteractor
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.repository.GenresRepository
import com.softvision.domain.repository.ItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class InteractorModule {

    @Provides
    @MoviesInteractor
    fun providesMoviesInteractor(@MoviesRepository repository: ItemsRepository<String, TMDBItemDetails, Int>): BaseFetchItemsUseCase<String, TMDBItemDetails, Int> {
        return FetchMoviesInteractor(repository)
    }

    @Provides
    @TvShowsInteractor
    fun providesTVShowsInteractor(@TvShowsRepository repository: ItemsRepository<String, TMDBItemDetails, Int>): BaseFetchItemsUseCase<String, TMDBItemDetails, Int> {
        return FetchTVShowsInteractor(repository)
    }

    @Provides
    @QueryInteractor
    fun providesQueryInteractor(@QueryRepository repository: ItemsRepository<String, TMDBItemDetails, Int>): BaseFetchItemsUseCase<String, TMDBItemDetails, Int> {
        return FetchQueryInteractor(repository)
    }

    @Provides
    fun bindGenresInteractor(repository: GenresRepository<TMDBGenre>): BaseFetchGenresUseCase<TMDBGenre> {
        return FetchMoviesGenresInteractor(repository)
    }
}