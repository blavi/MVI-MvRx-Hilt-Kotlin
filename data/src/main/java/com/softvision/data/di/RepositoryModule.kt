package com.softvision.data.di

import com.softvision.data.repository.ExplorerMoviesRepositoryImpl
import com.softvision.data.repository.ExplorerTVShowsRepositoryImpl
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.repository.ResourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExplorerMoviesRepository(impl: ExplorerMoviesRepositoryImpl): ResourcesRepository<String, TMDBMovieDetails, Int>

    @Binds
    abstract fun bindExplorerTVShowsRepository(impl: ExplorerTVShowsRepositoryImpl): ResourcesRepository<String, TMDBTVShowDetails, Int>
}