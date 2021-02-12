package com.softvision.data.database.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.softvision.data.database.dao.TMDBItemsDAO
import com.softvision.data.database.model.TMDBItemEntity

@Database(entities = [TMDBItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(IntListConverter::class, StringListConverter::class)
abstract class EntitiesDatabase : RoomDatabase() {

    abstract fun getItemsDAO(): TMDBItemsDAO

//    abstract fun getRoomsDAO(): RoomsDAO
}