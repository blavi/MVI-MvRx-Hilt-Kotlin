package com.softvision.data.di

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TMDBMovieGenresDAO
import com.softvision.data.database.dao.TMDBMoviesDAO
import com.softvision.data.database.dao.TMDBTVShowsDAO
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.repository.ItemsQueryRepositoryImpl
import com.softvision.data.repository.MovieGenresRepositoryImpl
import com.softvision.data.repository.MoviesRepositoryImpl
import com.softvision.data.repository.TVShowsRepositoryImpl
import com.softvision.domain.di.MoviesRepository
import com.softvision.domain.di.QueryRepository
import com.softvision.domain.di.TvShowsRepository
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
class RepositoryModule {

    @Provides
    @MoviesRepository
    fun provideExplorerMoviesRepository(tmdbMoviesDAO: TMDBMoviesDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, TMDBItemDetails, Int> {
        return MoviesRepositoryImpl(tmdbMoviesDAO, resourcesApi, connectivity)
    }

    @Provides
    @TvShowsRepository
    fun provideExplorerTVShowsRepository(tmdbTVShowsDAO: TMDBTVShowsDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, TMDBItemDetails, Int> {
        return TVShowsRepositoryImpl(tmdbTVShowsDAO, resourcesApi, connectivity)
    }

    @Provides
    @QueryRepository
    fun provideDiscoveryRepository(tmdbMoviesDAO: TMDBMoviesDAO,tmdbTVShowsDAO: TMDBTVShowsDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): ItemsRepository<String, TMDBItemDetails, Int> {
        return ItemsQueryRepositoryImpl(tmdbMoviesDAO, tmdbTVShowsDAO, resourcesApi, connectivity)
    }

    @Provides
    fun provideMovieGenresRepository(genresDAO: TMDBMovieGenresDAO, resourcesApi: ApiEndpoints, connectivity: Connectivity): GenresRepository<TMDBGenre> {
        return MovieGenresRepositoryImpl(genresDAO, resourcesApi, connectivity)
    }
}