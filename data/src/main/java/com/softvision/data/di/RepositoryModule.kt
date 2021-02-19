package com.softvision.data.di

import com.softvision.data.repository.MovieGenresRepositoryImpl
import com.softvision.data.repository.MoviesRepositoryImpl
import com.softvision.data.repository.TVShowsRepositoryImpl
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.repository.GenresRepository
import com.softvision.domain.repository.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExplorerMoviesRepository(impl: MoviesRepositoryImpl): ItemsRepository<String, TMDBMovieDetails, Int>

    @Binds
    abstract fun bindExplorerTVShowsRepository(impl: TVShowsRepositoryImpl): ItemsRepository<String, TMDBTVShowDetails, Int>

    @Binds
    abstract fun bindMovieGenresRepository(impl: MovieGenresRepositoryImpl): GenresRepository<TMDBGenre>
}