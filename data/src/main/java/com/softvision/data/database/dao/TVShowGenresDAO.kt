package com.softvision.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softvision.data.database.base.TMDB_TV_SHOW_GENRES
import com.softvision.data.database.model.TvShowGenreEntity
import io.reactivex.Single

@Dao
interface TVShowGenresDAO {
    @Query("SELECT * FROM $TMDB_TV_SHOW_GENRES ORDER BY LOWER(NAME) ASC")
    fun loadAllItems(): Single<List<TvShowGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<TvShowGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TvShowGenreEntity)

    @Query("DELETE FROM $TMDB_TV_SHOW_GENRES")
    fun deleteAll()

    @Query("SELECT * FROM $TMDB_TV_SHOW_GENRES WHERE ID = :itemID LIMIT 1")
    fun getItem(itemID: Int): TvShowGenreEntity?
}