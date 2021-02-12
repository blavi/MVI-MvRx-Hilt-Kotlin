package com.softvision.data.database.dao

import androidx.room.*
import com.softvision.data.database.base.TMDB_ITEMS
import com.softvision.data.database.model.PartialTMDBItemEntity
import com.softvision.data.database.model.TMDBItemEntity

@Dao
interface TMDBItemsDAO {
    @Query("SELECT * FROM $TMDB_ITEMS ORDER BY ID")
    fun loadAllItems(): List<TMDBItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<TMDBItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TMDBItemEntity)

    @Query("DELETE FROM $TMDB_ITEMS")
    fun deleteAll()

    @Update(entity = TMDBItemEntity::class)
    fun update(partialItem: PartialTMDBItemEntity)

    @Query("SELECT * FROM $TMDB_ITEMS WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): TMDBItemEntity?

    @Query("SELECT * FROM $TMDB_ITEMS WHERE :category in (CATEGORIES)")
    fun loadItemsByCategory(category: String): List<TMDBItemEntity>
}