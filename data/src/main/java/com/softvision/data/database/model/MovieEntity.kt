package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softvision.data.database.base.TMDB_MOVIES
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
    val backdropPath : String?,

    @ColumnInfo(name = "genre_ids")
    @Json(name = "genre_ids")
    val genreIds : List<Int>? = null,

    @ColumnInfo(name = "original_language")
    val originalLanguage : String,

    @ColumnInfo(name = "original_title")
    val originalTitle : String,

    @ColumnInfo(name = "overview")
    val overview : String,

    @ColumnInfo(name = "popularity")
    val popularity : Double?,

    @ColumnInfo(name = "poster_path")
    val posterPath : String?,

    @ColumnInfo(name = "release_date")
    val releaseDate : String,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "video")
    val video : Boolean,

    @ColumnInfo(name = "vote_average")
    val voteAverage : Double,

    @ColumnInfo(name = "vote_count")
    val voteCount : Int,

    @ColumnInfo(name = "media_type")
    val mediaType : String?,

    @ColumnInfo(name = "categories")
    val categories : List<String>
): BaseItemEntity() {
    override fun mapToDomainModel(): MovieDetails {
        return MovieDetails(id, adult, backdropPath, genreIds, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount,
            mediaType, categories)
    }

}
