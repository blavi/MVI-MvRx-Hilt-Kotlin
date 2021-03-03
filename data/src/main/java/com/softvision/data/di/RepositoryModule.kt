package com.softvision.data.di

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MovieGenresDAO
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.repository.ItemsQueryRepositoryImpl
import com.softvision.data.repository.MovieGenresRepositoryImpl
import com.softvision.data.repository.MoviesRepositoryImpl
import com.softvision.data.repository.TVShowsRepositoryImpl
import com.softvision.domain.di.MoviesRepository
import com.softvision.domain.di.QueryRepository
import com.softvision.domain.di.TvShowsRepository
import com.softvision.domain.model.Genre
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
    fun provideMovieGenresRepository(genresDAO: MovieGenresDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): GenresRepository<Genre> {
        return MovieGenresRepositoryImpl(genresDAO, resourcesApi, connectivity)
    }
}