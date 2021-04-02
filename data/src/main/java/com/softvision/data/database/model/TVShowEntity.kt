package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softvision.data.database.base.TMDB_TV_SHOWS
import com.softvision.domain.model.TVShowDetails

@Entity(tableName = TMDB_TV_SHOWS)
data class TVShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath : String?,

    @ColumnInfo(name = "genre_ids")
    val genreIds : List<Int>,

    @ColumnInfo(name = "origin_country")
    val originCountry : List<String>,

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

    @ColumnInfo(name = "vote_average")
    val voteAverage : Double,

    @ColumnInfo(name = "vote_count")
    val voteCount : Int,

    @ColumnInfo(name = "categories")
    val categories : List<String>
): BaseItemEntity() {
    override fun mapToDomainModel(): TVShowDetails {
        return TVShowDetails(id, backdropPath, genreIds, originCountry, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, voteAverage, voteCount,
            categories)
    }
}
