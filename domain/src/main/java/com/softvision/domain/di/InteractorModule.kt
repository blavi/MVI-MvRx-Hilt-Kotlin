package com.softvision.domain.di

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.interactor.FetchMoviesGenresInteractor
import com.softvision.domain.interactor.FetchMoviesInteractor
import com.softvision.domain.interactor.FetchQueryInteractor
import com.softvision.domain.interactor.FetchTVShowsInteractor
import com.softvision.domain.model.Genre
import com.softvision.domain.model.base.ItemDetails
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
    fun providesMoviesInteractor(@MoviesRepository repository: ItemsRepository<String, ItemDetails, Int>): BaseFetchItemsUseCase<String, ItemDetails, Int> {
        return FetchMoviesInteractor(repository)
    }

    @Provides
    @TvShowsInteractor
    fun providesTVShowsInteractor(@TvShowsRepository repository: ItemsRepository<String, ItemDetails, Int>): BaseFetchItemsUseCase<String, ItemDetails, Int> {
        return FetchTVShowsInteractor(repository)
    }

    @Provides
    @QueryInteractor
    fun providesQueryInteractor(@QueryRepository repository: ItemsRepository<String, ItemDetails, Int>): BaseFetchItemsUseCase<String, ItemDetails, Int> {
        return FetchQueryInteractor(repository)
    }

    @Provides
    fun bindGenresInteractor(repository: GenresRepository<Genre>): BaseFetchGenresUseCase<Genre> {
        return FetchMoviesGenresInteractor(repository)
    }
}