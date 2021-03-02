package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softvision.data.database.base.TMDB_MOVIES
import com.softvision.data.database.model.base.ItemEntity
import com.softvision.domain.model.MovieDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = TMDB_MOVIES)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "adult")
    val adult : Boolean? = true,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path : String?,

    @ColumnInfo(name = "genre_ids")
    @Json(name = "genre_ids")
    val genre_ids : List<Int>? = null,

    @ColumnInfo(name = "original_language")
    val original_language : String,

    @ColumnInfo(name = "original_title")
    val original_title : String,

    @ColumnInfo(name = "overview")
    val overview : String,

    @ColumnInfo(name = "popularity")
    val popularity : Double,

    @ColumnInfo(name = "poster_path")
    val poster_path : String?,

    @ColumnInfo(name = "release_date")
    val release_date : String,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "video")
    val video : Boolean,

    @ColumnInfo(name = "vote_average")
    val vote_average : Double,

    @ColumnInfo(name = "vote_count")
    val vote_count : Int,

    @ColumnInfo(name = "media_type")
    val media_type : String?,

    @ColumnInfo(name = "categories")
    val categories : List<String>
): ItemEntity() {
    override fun mapToDomainModel(): MovieDetails {
        return MovieDetails(id, adult, backdrop_path, genre_ids, original_language, original_title,
            overview, popularity, poster_path, release_date, title, video, vote_average, vote_count,
            media_type, categories)
    }

}
