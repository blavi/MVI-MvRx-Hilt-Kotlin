package com.softvision.data.network.model

import com.softvision.data.database.model.*
import com.softvision.data.network.utils.ItemType
import com.softvision.domain.model.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


sealed class Item(@Json(name="media_type") val type: ItemType? = null):
    BaseItemResponse<BaseItemEntity, String, BaseItemDetails>

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "adult") val adult : Boolean,
    @Json(name = "backdrop_path") val backdropPath : String?,
    @Json(name = "genre_ids") val genreIds : List<Int>,
    @Json(name = "original_language") val originalLanguage : String,
    @Json(name = "original_title") val originalTitle : String,
    @Json(name = "overview") val overview : String,
    @Json(name = "popularity") val popularity : Double?,
    @Json(name = "poster_path") val posterPath : String?,
    @Json(name = "release_date") val releaseDate : String,
    @Json(name = "title") val title : String,
    @Json(name = "video") val video : Boolean,
    @Json(name = "vote_average") val voteAverage : Double,
    @Json(name = "vote_count") val voteCount : Int,
    @Json(name = "media_type") val mediaType : String?
) : Item(ItemType.movie){
    override fun mapToRoomEntity(categories: List<String>): MovieEntity {
        return MovieEntity(id, adult, backdropPath, genreIds, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, video, voteAverage,
            voteCount, mediaType, categories)
    }

    override fun mapToDomainModel(): MovieDetails {
        return MovieDetails(id, adult, backdropPath, genreIds, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, video, voteAverage,
            voteCount, mediaType, emptyList())
    }

    override fun mapToRoomEntity(): MovieEntity {
        return MovieEntity(id, adult, backdropPath, genreIds, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, video, voteAverage,
            voteCount, mediaType, emptyList())
    }
}

@JsonClass(generateAdapter = true)
data class TVShowResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "backdrop_path") val backdropPath : String?,
    @Json(name = "genre_ids") val genreIds : List<Int>,
    @Json(name = "origin_country") val originCountry: List<String>,
    @Json(name = "original_language") val originalLanguage : String,
    @Json(name = "original_name") val originalTitle : String,
    @Json(name = "overview") val overview : String,
    @Json(name = "popularity") val popularity : Double?,
    @Json(name = "poster_path") val posterPath : String?,
    @Json(name = "first_air_date") val releaseDate : String,
    @Json(name = "name") val title : String,
    @Json(name = "vote_average") val voteAverage : Double,
    @Json(name = "vote_count") val voteCount : Int,
    @Json(name = "media_type") val mediaType : String?
) : Item(ItemType.tv) {
    override fun mapToRoomEntity(categories: List<String>): TVShowEntity {
        return TVShowEntity(id, backdropPath, genreIds, originCountry, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, voteAverage,
            voteCount, categories)
    }

    override fun mapToRoomEntity(): TVShowEntity {
        return TVShowEntity(id, backdropPath, genreIds, originCountry, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, voteAverage,
            voteCount, emptyList())
    }

    override fun mapToDomainModel(): TVShowDetails {
        return TVShowDetails(id, backdropPath, genreIds, originCountry, originalLanguage, originalTitle,
            overview, popularity, posterPath, releaseDate, title, voteAverage,
            voteCount, emptyList())
    }
}

@JsonClass(generateAdapter = true)
data class PersonResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "profile_path") val profilePath : String?,
    @Json(name = "adult") val adult : Boolean,
    @Json(name = "media_type") val mediaType : String,
    @Json(name = "known_for") val knownFor : List<Item>,
    @Json(name = "known_for_department") val knownForDepartment : String,
    @Json(name = "name") val name : String,
    @Json(name = "gender") val gender : Int,
    @Json(name = "popularity") val popularity : Double?
) : Item(ItemType.person) {
    override fun mapToDomainModel(): PersonDetails {
        return PersonDetails(id, profilePath, adult, emptyList(), knownForDepartment,
            name, gender, popularity
        )
    }

    override fun mapToRoomEntity(categories: List<String>): PersonEntity {
        return PersonEntity(id, adult, popularity, profilePath, emptyList(), knownForDepartment, name, gender)
    }

    override fun mapToRoomEntity(): PersonEntity {
        return PersonEntity(id, adult, popularity, profilePath, emptyList(), knownForDepartment, name, gender)
    }
}