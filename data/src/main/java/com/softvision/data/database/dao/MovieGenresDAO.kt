package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_MOVIE_GENRES
import com.softvision.data.database.model.MovieGenreEntity
import io.reactivex.Single

@Dao
interface MovieGenresDAO {
    @Query("SELECT * FROM $TMDB_MOVIE_GENRES ORDER BY ID")
    fun loadAllItems(): Single<List<MovieGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<MovieGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: MovieGenreEntity)

    @Query("DELETE FROM $TMDB_MOVIE_GENRES")
    fun deleteAll()

//    @Update(entity = TMDBGenreEntity::class)
//    fun update(partialItem: PartialTMDBMovieEntity)

    @Query("SELECT * FROM $TMDB_MOVIE_GENRES WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): MovieGenreEntity?

//    @Query("SELECT * FROM $TMDB_MOVIE_GENRES WHERE :category in (CATEGORIES)")
//    fun loadItemsByCategory(category: String): List<TMDBMovieEntity>
}