package com.softvision.domain.di

import com.softvision.domain.base.BaseFetchGenresUseCase
import com.softvision.domain.base.BaseFetchItemsUseCase
import com.softvision.domain.interactor.*
import com.softvision.domain.model.BaseItemDetails
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
    fun providesMoviesInteractor(@MoviesRepository repository: ItemsRepository<String, BaseItemDetails, Int>): BaseFetchItemsUseCase<String, BaseItemDetails, Int> {
        return FetchMoviesInteractor(repository)
    }

    @Provides
    @MoviesByGenreInteractor
    fun providesMoviesByGenreInteractor(@MoviesByGenreRepository repository: ItemsRepository<String, BaseItemDetails, Int>): BaseFetchItemsUseCase<String, BaseItemDetails, Int> {
        return FetchMoviesByGenreInteractor(repository)
    }

    @Provides
    @TvShowsByGenreInteractor
    fun providesTvShowsByGenreInteractor(@TvShowsByGenreRepository repository: ItemsRepository<String, BaseItemDetails, Int>): BaseFetchItemsUseCase<String, BaseItemDetails, Int> {
        return FetchTVShowsByGenreInteractor(repository)
    }

    @Provides
    @TvShowsInteractor
    fun providesTVShowsInteractor(@TvShowsRepository repository: ItemsRepository<String, BaseItemDetails, Int>): BaseFetchItemsUseCase<String, BaseItemDetails, Int> {
        return FetchTVShowsInteractor(repository)
    }

    @Provides
    @QueryInteractor
    fun providesQueryInteractor(@QueryRepository repository: ItemsRepository<String, BaseItemDetails, Int>): BaseFetchItemsUseCase<String, BaseItemDetails, Int> {
        return FetchQueryInteractor(repository)
    }

    @Provides
    @MovieGenresInteractor
    fun providesMovieGenresInteractor(@MovieGenresRepository repository: GenresRepository<BaseItemDetails>): BaseFetchGenresUseCase<BaseItemDetails> {
        return FetchMoviesGenresInteractor(repository)
    }

    @Provides
    @TvShowGenresInteractor
    fun providesTvShowGenresInteractor(@TvShowGenresRepository repository: GenresRepository<BaseItemDetails>): BaseFetchGenresUseCase<BaseItemDetails> {
        return FetchTVShowsGenresInteractor(repository)
    }
}