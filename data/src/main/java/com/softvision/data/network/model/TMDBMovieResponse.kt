package com.softvision.data.network.model

import com.softvision.data.database.model.TMDBMovieEntity
import com.softvision.data.mappers.ItemRoomMapper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBMovieResponse (
    @Json(name = "id") val id : Int,
    @Json(name = "adult") val adult : Boolean,
    @Json(name = "backdrop_path") val backdrop_path : String?,
    @Json(name = "genre_ids") val genre_ids : List<Int>,
    @Json(name = "original_language") val original_language : String,
    @Json(name = "original_title") val original_title : String,
    @Json(name = "overview") val overview : String,
    @Json(name = "popularity") val popularity : Double,
    @Json(name = "poster_path") val poster_path : String?,
    @Json(name = "release_date") val release_date : String,
    @Json(name = "title") val title : String,
    @Json(name = "video") val video : Boolean,
    @Json(name = "vote_average") val vote_average : Double,
    @Json(name = "vote_count") val vote_count : Int,
    @Json(name = "media_type") val media_type : String?
): ItemRoomMapper<TMDBMovieEntity, String> {
    override fun mapToRoomEntity(categories: List<String>): TMDBMovieEntity {
        return TMDBMovieEntity(id, adult, backdrop_path, genre_ids, original_language, original_title,
        overview, popularity, poster_path, release_date, title, video, vote_average,
        vote_count, media_type, categories)
    }
}
