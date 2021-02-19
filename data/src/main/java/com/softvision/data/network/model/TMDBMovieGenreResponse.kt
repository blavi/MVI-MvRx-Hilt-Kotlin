package com.softvision.data.network.model

import com.softvision.data.database.model.TMDBMovieGenreEntity
import com.softvision.data.network.base.GenreRoomMapper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBMovieGenreResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "name") val name : String
): GenreRoomMapper<TMDBMovieGenreEntity> {
    override fun mapToRoomEntity(): TMDBMovieGenreEntity {
        return TMDBMovieGenreEntity(id, name)
    }
}
