package com.softvision.data.database.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softvision.data.database.dao.MovieGenresDAO
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.database.dao.TVShowGenresDAO
import com.softvision.data.database.model.MovieEntity
import com.softvision.data.database.model.MovieGenreEntity
import com.softvision.data.database.model.TVShowEntity
import com.softvision.data.database.model.TvShowGenreEntity

@Database(entities = [MovieEntity::class, TVShowEntity::class, MovieGenreEntity::class, TvShowGenreEntity::class], version = 1, exportSchema = false)
@TypeConverters(IntListConverter::class, StringListConverter::class)
abstract class EntitiesDatabase : RoomDatabase() {

    abstract fun getMoviesDAO(): MoviesDAO

    abstract fun getTVShowsDAO(): TVShowsDAO

    abstract fun getMovieGenresDAO(): MovieGenresDAO

    abstract fun getTvShowGenresDAO(): TVShowGenresDAO
}