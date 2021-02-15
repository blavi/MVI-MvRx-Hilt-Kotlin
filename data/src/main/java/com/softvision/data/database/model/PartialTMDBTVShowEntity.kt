package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class PartialTMDBTVShowEntity(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "categories")
    val categories: List<String>,
)