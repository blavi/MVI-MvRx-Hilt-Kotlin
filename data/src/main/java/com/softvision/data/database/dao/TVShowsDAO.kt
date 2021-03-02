package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_TV_SHOWS
import com.softvision.data.database.model.PartialTVShowEntity
import com.softvision.data.database.model.TVShowEntity
import io.reactivex.Single

@Dao
interface TVShowsDAO {
    @Query("SELECT * FROM $TMDB_TV_SHOWS ORDER BY ID")
    fun loadAllItems(): Single<List<TVShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<TVShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TVShowEntity)

    @Query("DELETE FROM $TMDB_TV_SHOWS")
    fun deleteAll()

    @Update(entity = TVShowEntity::class)
    fun update(partialItem: PartialTVShowEntity)

    @Query("SELECT * FROM $TMDB_TV_SHOWS WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): TVShowEntity?

    @Query("SELECT * FROM $TMDB_TV_SHOWS WHERE :category in (CATEGORIES)")
    fun loadItemsByCategory(category: String): Single<List<TVShowEntity>>

    @Query("SELECT * FROM $TMDB_TV_SHOWS WHERE :title in (TITLE)")
    fun loadItemsByTitle(title: String): Single<List<TVShowEntity>>
}