package com.softvision.data.di

import com.softvision.data.network.base.DataType
import com.softvision.data.repository.ExplorerRepositoryImpl
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.repository.ResourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExplorerRepository(impl: ExplorerRepositoryImpl): ResourcesRepository<String, TMDBItemDetails, Int>

//    @Binds
//    abstract fun bindRoomsRepository(impl: RoomsRepositoryImpl): ResourcesRepository<RoomDetails>
}