package com.softvision.data.network.model

import com.softvision.data.database.model.TvShowGenreEntity
import com.softvision.data.mappers.ItemRoomMapper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowGenreResponse(
    @Json(name = "id") val id : Int,
    @Json(name = "name") val name : String
): ItemRoomMapper<TvShowGenreEntity> {
    override fun mapToRoomEntity(): TvShowGenreEntity {
        return TvShowGenreEntity(id, name)
    }
}
