package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_MOVIE_GENRES
import com.softvision.data.database.model.MovieGenreEntity
import io.reactivex.Single

@Dao
interface MovieGenresDAO {
    @Query("SELECT * FROM $TMDB_MOVIE_GENRES ORDER BY LOWER(NAME) ASC")
    fun loadAllItems(): Single<List<MovieGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<MovieGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: MovieGenreEntity)

    @Query("DELETE FROM $TMDB_MOVIE_GENRES")
    fun deleteAll()

    @Query("SELECT * FROM $TMDB_MOVIE_GENRES WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): MovieGenreEntity?
}