package com.softvision.data.di

import android.content.Context
import androidx.room.Room
import com.softvision.data.database.base.EntitiesDatabase
import com.softvision.data.database.dao.TMDBMovieGenresDAO
import com.softvision.data.database.dao.TMDBMoviesDAO
import com.softvision.data.database.dao.TMDBTVShowsDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideMoviesDAO(appDatabase: EntitiesDatabase): TMDBMoviesDAO {
        return appDatabase.getMoviesDAO()
    }

    @Provides
    @Singleton
    fun provideTVShowsDAO(appDatabase: EntitiesDatabase): TMDBTVShowsDAO {
        return appDatabase.getTVShowsDAO()
    }

    @Provides
    @Singleton
    fun provideMovieGenresDAO(appDatabase: EntitiesDatabase): TMDBMovieGenresDAO {
        return appDatabase.getMovieGenresDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): EntitiesDatabase {
        return Room.databaseBuilder(
            appContext,
            EntitiesDatabase::class.java,
            "EntitiesDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}