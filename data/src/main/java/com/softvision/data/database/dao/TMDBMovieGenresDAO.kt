package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_MOVIE_GENRES
import com.softvision.data.database.model.TMDBMovieGenreEntity
import com.softvision.data.database.model.TMDBMovieEntity
import io.reactivex.Single

@Dao
interface TMDBMovieGenresDAO {
    @Query("SELECT * FROM $TMDB_MOVIE_GENRES ORDER BY ID")
    fun loadAllItems(): Single<List<TMDBMovieGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<TMDBMovieGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TMDBMovieGenreEntity)

    @Query("DELETE FROM $TMDB_MOVIE_GENRES")
    fun deleteAll()

//    @Update(entity = TMDBGenreEntity::class)
//    fun update(partialItem: PartialTMDBMovieEntity)

    @Query("SELECT * FROM $TMDB_MOVIE_GENRES WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): TMDBMovieGenreEntity?

//    @Query("SELECT * FROM $TMDB_MOVIE_GENRES WHERE :category in (CATEGORIES)")
//    fun loadItemsByCategory(category: String): List<TMDBMovieEntity>
}