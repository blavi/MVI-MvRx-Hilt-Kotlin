package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softvision.data.database.base.TMDB_MOVIES
import com.softvision.data.database.base.TMDB_TV_SHOWS
import com.softvision.data.network.base.DomainMapper
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails

@Entity(tableName = TMDB_TV_SHOWS)
data class TMDBTVShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path : String?,

    @ColumnInfo(name = "genre_ids")
    val genre_ids : List<Int>,

    @ColumnInfo(name = "origin_country")
    val origin_country : List<String>,

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

    @ColumnInfo(name = "vote_average")
    val vote_average : Double,

    @ColumnInfo(name = "vote_count")
    val vote_count : Int,

    @ColumnInfo(name = "categories")
    val categories : List<String>
): DomainMapper<TMDBTVShowDetails> {
    override fun mapToDomainModel(): TMDBTVShowDetails {
        return TMDBTVShowDetails(id, backdrop_path, genre_ids, origin_country, original_language, original_title,
            overview, popularity, poster_path, release_date, title, vote_average, vote_count,
            categories)
    }
}
