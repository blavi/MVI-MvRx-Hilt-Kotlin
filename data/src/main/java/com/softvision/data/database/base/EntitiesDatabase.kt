package com.softvision.data.database.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softvision.data.database.dao.TMDBMovieGenresDAO
import com.softvision.data.database.dao.TMDBMoviesDAO
import com.softvision.data.database.dao.TMDBTVShowsDAO
import com.softvision.data.database.model.TMDBMovieEntity
import com.softvision.data.database.model.TMDBMovieGenreEntity
import com.softvision.data.database.model.TMDBTVShowEntity

@Database(entities = [TMDBMovieEntity::class, TMDBTVShowEntity::class, TMDBMovieGenreEntity::class], version = 1, exportSchema = false)
@TypeConverters(IntListConverter::class, StringListConverter::class)
abstract class EntitiesDatabase : RoomDatabase() {

    abstract fun getMoviesDAO(): TMDBMoviesDAO

    abstract fun getTVShowsDAO(): TMDBTVShowsDAO

    abstract fun getMovieGenresDAO(): TMDBMovieGenresDAO
}