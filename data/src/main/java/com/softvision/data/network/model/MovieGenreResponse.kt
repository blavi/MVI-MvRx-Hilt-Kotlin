package com.softvision.data.network.model

import com.softvision.data.database.model.MovieGenreEntity
import com.softvision.data.mappers.GenreRoomMapper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenreResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "name") val name : String
): GenreRoomMapper<MovieGenreEntity> {
    override fun mapToRoomEntity(): MovieGenreEntity {
        return MovieGenreEntity(id, name)
    }
}
