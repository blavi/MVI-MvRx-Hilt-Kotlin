package com.softvision.data.di

import android.content.Context
import com.softvision.data.common.Connectivity
import com.softvision.data.common.ConnectivityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun provideConnectivity(@ApplicationContext appContext: Context): Connectivity {
        return ConnectivityImpl(appContext)
    }
}