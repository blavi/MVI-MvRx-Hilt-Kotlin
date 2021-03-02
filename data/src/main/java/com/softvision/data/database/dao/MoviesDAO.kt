package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_MOVIES
import com.softvision.data.database.model.PartialMovieEntity
import com.softvision.data.database.model.MovieEntity
import io.reactivex.Single

@Dao
interface MoviesDAO {
    @Query("SELECT * FROM $TMDB_MOVIES ORDER BY ID")
    fun loadAllItems(): Single<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: MovieEntity)

    @Query("DELETE FROM $TMDB_MOVIES")
    fun deleteAll()

    @Update(entity = MovieEntity::class)
    fun update(partialItem: PartialMovieEntity)

    @Query("SELECT * FROM $TMDB_MOVIES WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): MovieEntity?

    @Query("SELECT * FROM $TMDB_MOVIES WHERE :category in (CATEGORIES)")
    fun loadItemsByCategory(category: String): Single<List<MovieEntity>>

    @Query("SELECT * FROM $TMDB_MOVIES WHERE :title in (TITLE)")
    fun loadItemsByTitle(title: String): Single<List<MovieEntity>>
}