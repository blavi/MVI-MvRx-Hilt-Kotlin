package com.softvision.data.di

import android.content.Context
import androidx.room.Room
import com.softvision.data.database.base.EntitiesDatabase
import com.softvision.data.database.dao.MovieGenresDAO
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.dao.TVShowsDAO
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
    fun provideMoviesDAO(appDatabase: EntitiesDatabase): MoviesDAO {
        return appDatabase.getMoviesDAO()
    }

    @Provides
    @Singleton
    fun provideTVShowsDAO(appDatabase: EntitiesDatabase): TVShowsDAO {
        return appDatabase.getTVShowsDAO()
    }

    @Provides
    @Singleton
    fun provideMovieGenresDAO(appDatabase: EntitiesDatabase): MovieGenresDAO {
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