package com.softvision.data.network.model

import com.softvision.data.database.model.*
import com.softvision.data.mappers.ItemRoomMapper
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
    @Json(name = "backdrop_path") val backdrop_path : String?,
    @Json(name = "genre_ids") val genre_ids : List<Int>,
    @Json(name = "original_language") val original_language : String,
    @Json(name = "original_title") val original_title : String,
    @Json(name = "overview") val overview : String,
    @Json(name = "popularity") val popularity : Double?,
    @Json(name = "poster_path") val poster_path : String?,
    @Json(name = "release_date") val release_date : String,
    @Json(name = "title") val title : String,
    @Json(name = "video") val video : Boolean,
    @Json(name = "vote_average") val vote_average : Double,
    @Json(name = "vote_count") val vote_count : Int,
    @Json(name = "media_type") val media_type : String?
) : Item(ItemType.movie){
    override fun mapToRoomEntity(categories: List<String>): MovieEntity {
        return MovieEntity(id, adult, backdrop_path, genre_ids, original_language, original_title,
            overview, popularity, poster_path, release_date, title, video, vote_average,
            vote_count, media_type, categories)
    }

    override fun mapToDomainModel(): MovieDetails {
        return MovieDetails(id, adult, backdrop_path, genre_ids, original_language, original_title,
            overview, popularity, poster_path, release_date, title, video, vote_average,
            vote_count, media_type, emptyList())
    }

    override fun mapToRoomEntity(): MovieEntity {
        return MovieEntity(id, adult, backdrop_path, genre_ids, original_language, original_title,
            overview, popularity, poster_path, release_date, title, video, vote_average,
            vote_count, media_type, emptyList())
    }
}

@JsonClass(generateAdapter = true)
data class TVShowResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "backdrop_path") val backdrop_path : String?,
    @Json(name = "genre_ids") val genre_ids : List<Int>,
    @Json(name = "origin_country") val origin_country: List<String>,
    @Json(name = "original_language") val original_language : String,
    @Json(name = "original_name") val original_title : String,
    @Json(name = "overview") val overview : String,
    @Json(name = "popularity") val popularity : Double?,
    @Json(name = "poster_path") val poster_path : String?,
    @Json(name = "first_air_date") val release_date : String,
    @Json(name = "name") val title : String,
    @Json(name = "vote_average") val vote_average : Double,
    @Json(name = "vote_count") val vote_count : Int,
    @Json(name = "media_type") val media_type : String?
) : Item(ItemType.tv) {
    override fun mapToRoomEntity(categories: List<String>): TVShowEntity {
        return TVShowEntity(id, backdrop_path, genre_ids, origin_country, original_language, original_title,
            overview, popularity, poster_path, release_date, title, vote_average,
            vote_count, categories)
    }

    override fun mapToRoomEntity(): TVShowEntity {
        return TVShowEntity(id, backdrop_path, genre_ids, origin_country, original_language, original_title,
            overview, popularity, poster_path, release_date, title, vote_average,
            vote_count, emptyList())
    }

    override fun mapToDomainModel(): TVShowDetails {
        return TVShowDetails(id, backdrop_path, genre_ids, origin_country, original_language, original_title,
            overview, popularity, poster_path, release_date, title, vote_average,
            vote_count, emptyList())
    }
}

@JsonClass(generateAdapter = true)
data class PersonResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "profile_path") val profile_path : String?,
    @Json(name = "adult") val adult : Boolean,
    @Json(name = "media_type") val media_type : String,
    @Json(name = "known_for") val known_for : List<Item>,
    @Json(name = "known_for_department") val known_for_department : String,
    @Json(name = "name") val name : String,
    @Json(name = "gender") val gender : Int,
    @Json(name = "popularity") val popularity : Double?
) : Item(ItemType.person) {
    override fun mapToDomainModel(): PersonDetails {
        return PersonDetails(id, profile_path, adult, emptyList(), known_for_department,
            name, gender, popularity
        )
    }

    override fun mapToRoomEntity(categories: List<String>): PersonEntity {
        return PersonEntity(id, adult, popularity, profile_path, emptyList(), known_for_department, name, gender)
    }

    override fun mapToRoomEntity(): PersonEntity {
        return PersonEntity(id, adult, popularity, profile_path, emptyList(), known_for_department, name, gender)
    }
}

//@JsonClass(generateAdapter = true)
//data class MovieGenreResponse(
//    @Json(name = "id") val id : Int,
//    @Json(name = "name") val name : String
//): Item() {
//    override fun mapToRoomEntity(categories: List<String>): MovieGenreEntity {
//        return MovieGenreEntity(id, name)
//    }
//
//    override fun mapToRoomEntity(): MovieGenreEntity {
//        return MovieGenreEntity(id, name)
//    }
//
//    override fun mapToDomainModel(): Genre {
//        return Genre(id, name)
//    }
//}

//ItemRoomMapper<MovieGenreEntity> {
//    override fun mapToRoomEntity(): MovieGenreEntity {
//        return MovieGenreEntity(id, name)
//    }