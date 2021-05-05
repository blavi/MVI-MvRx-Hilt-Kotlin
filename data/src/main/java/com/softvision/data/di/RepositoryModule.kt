package com.softvision.data.di

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MovieGenresDAO
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.dao.TVShowGenresDAO
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.repository.*
import com.softvision.domain.di.*
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.GenresRepository
import com.softvision.domain.repository.ItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @MoviesRepository
    fun provideExplorerMoviesRepository(moviesDAO: MoviesDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, BaseItemDetails, Int> {
        return MoviesRepositoryImpl(moviesDAO, resourcesApi, connectivity)
    }

    @Provides
    @TvShowsRepository
    fun provideExplorerTVShowsRepository(tvShowsDAO: TVShowsDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, BaseItemDetails, Int> {
        return TVShowsRepositoryImpl(tvShowsDAO, resourcesApi, connectivity)
    }

    @Provides
    @QueryRepository
    fun provideDiscoveryRepository(moviesDAO: MoviesDAO, tvShowsDAO: TVShowsDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, BaseItemDetails, Int> {
        return ItemsQueryRepositoryImpl(moviesDAO, tvShowsDAO, resourcesApi, connectivity)
    }

    @Provides
    @MovieGenresRepository
    fun provideMovieGenresRepository(genresDAO: MovieGenresDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): GenresRepository<BaseItemDetails> {
        return MovieGenresRepositoryImpl(genresDAO, resourcesApi, connectivity)
    }

    @Provides
    @TvShowGenresRepository
    fun provideTvShowGenresRepository(genresDAO: TVShowGenresDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): GenresRepository<BaseItemDetails> {
        return TVShowGenresRepositoryImpl(genresDAO, resourcesApi, connectivity)
    }

    @Provides
    @MoviesByGenreRepository
    fun provideMovieByGenreRepository(moviesDAO: MoviesDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, BaseItemDetails, Int> {
        return MoviesByGenreRepositoryImpl(moviesDAO, resourcesApi, connectivity)
    }

    @Provides
    @TvShowsByGenreRepository
    fun provideTvShowsByGenreRepository(tvShowsDAO: TVShowsDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, BaseItemDetails, Int> {
        return TVShowsByGenreRepositoryImpl(tvShowsDAO, resourcesApi, connectivity)
    }
}