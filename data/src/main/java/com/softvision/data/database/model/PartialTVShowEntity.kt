package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class PartialTVShowEntity(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "categories")
    val categories: List<String>,
)