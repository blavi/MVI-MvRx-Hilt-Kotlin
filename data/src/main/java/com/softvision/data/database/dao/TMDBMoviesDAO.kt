package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_MOVIES
import com.softvision.data.database.model.PartialTMDBMovieEntity
import com.softvision.data.database.model.TMDBMovieEntity
import io.reactivex.Single

@Dao
interface TMDBMoviesDAO {
    @Query("SELECT * FROM $TMDB_MOVIES ORDER BY ID")
    fun loadAllItems(): Single<List<TMDBMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<TMDBMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TMDBMovieEntity)

    @Query("DELETE FROM $TMDB_MOVIES")
    fun deleteAll()

    @Update(entity = TMDBMovieEntity::class)
    fun update(partialItem: PartialTMDBMovieEntity)

    @Query("SELECT * FROM $TMDB_MOVIES WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): TMDBMovieEntity?

    @Query("SELECT * FROM $TMDB_MOVIES WHERE :category in (CATEGORIES)")
    fun loadItemsByCategory(category: String): Single<List<TMDBMovieEntity>>
}