package com.softvision.data.network.model

import com.softvision.data.database.model.TMDBMovieEntity
import com.softvision.data.database.model.TMDBTVShowEntity
import com.softvision.data.network.base.RoomMapper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBTVShowResponse (
    @Json(name = "id") val id : Int,
    @Json(name = "backdrop_path") val backdrop_path : String?,
    @Json(name = "genre_ids") val genre_ids : List<Int>,
    @Json(name = "origin_country") val origin_country: List<String>,
    @Json(name = "original_language") val original_language : String,
    @Json(name = "original_name") val original_title : String,
    @Json(name = "overview") val overview : String,
    @Json(name = "popularity") val popularity : Double,
    @Json(name = "poster_path") val poster_path : String?,
    @Json(name = "first_air_date") val release_date : String,
    @Json(name = "name") val title : String,
    @Json(name = "vote_average") val vote_average : Double,
    @Json(name = "vote_count") val vote_count : Int,
): RoomMapper<TMDBTVShowEntity, String> {
    override fun mapToRoomEntity(categories: List<String>): TMDBTVShowEntity {
        return TMDBTVShowEntity(id, backdrop_path, genre_ids, origin_country, original_language, original_title,
            overview, popularity, poster_path, release_date, title, vote_average,
            vote_count, categories)
    }
}