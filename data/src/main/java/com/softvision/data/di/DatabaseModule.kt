package com.softvision.data.di

import android.content.Context
import androidx.room.Room
import com.softvision.data.database.base.EntitiesDatabase
import com.softvision.data.database.dao.TMDBItemsDAO
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
    fun providePersonsDAO(appDatabase: EntitiesDatabase): TMDBItemsDAO {
        return appDatabase.getItemsDAO()
    }

//    @Provides
//    @Singleton
//    fun provideRoomsDAO(appDatabase: EntitiesDatabase): RoomsDAO {
//        return appDatabase.getRoomsDAO()
//    }

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