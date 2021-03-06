package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softvision.data.database.base.TMDB_MOVIE_GENRES
import com.softvision.domain.model.GenreDetails

@Entity(tableName = TMDB_MOVIE_GENRES)
data class MovieGenreEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "name")
    val name : String
): BaseItemEntity() {
    override fun mapToDomainModel(): GenreDetails {
        return GenreDetails(id, name)
    }
}