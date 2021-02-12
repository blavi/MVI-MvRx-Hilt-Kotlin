package com.softvision.domain.di

import com.softvision.domain.interactor.ExplorerInteractor
import com.softvision.domain.interactor.FetchInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class InteractorModule {

    @Binds
    abstract fun bindPeopleInteractor(impl: ExplorerInteractor): FetchInteractor

//    @Binds
//    @Named("RoomsInteractor")
//    abstract fun bindRoomsInteractor(impl: FetchRoomsInteractor): FetchInteractor
}