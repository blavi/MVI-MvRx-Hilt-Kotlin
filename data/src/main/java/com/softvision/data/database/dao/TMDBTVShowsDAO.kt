package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_MOVIES
import com.softvision.data.database.base.TMDB_TV_SHOWS
import com.softvision.data.database.model.PartialTMDBTVShowEntity
import com.softvision.data.database.model.TMDBMovieEntity
import com.softvision.data.database.model.TMDBTVShowEntity
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface TMDBTVShowsDAO {
    @Query("SELECT * FROM $TMDB_TV_SHOWS ORDER BY ID")
    fun loadAllItems(): Single<List<TMDBTVShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<TMDBTVShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TMDBTVShowEntity)

    @Query("DELETE FROM $TMDB_TV_SHOWS")
    fun deleteAll()

    @Update(entity = TMDBTVShowEntity::class)
    fun update(partialItem: PartialTMDBTVShowEntity)

    @Query("SELECT * FROM $TMDB_TV_SHOWS WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): TMDBTVShowEntity?

    @Query("SELECT * FROM $TMDB_TV_SHOWS WHERE :category in (CATEGORIES)")
    fun loadItemsByCategory(category: String): Single<List<TMDBTVShowEntity>>

    @Query("SELECT * FROM $TMDB_TV_SHOWS WHERE :title in (TITLE)")
    fun loadItemsByTitle(title: String): Single<List<TMDBTVShowEntity>>
}