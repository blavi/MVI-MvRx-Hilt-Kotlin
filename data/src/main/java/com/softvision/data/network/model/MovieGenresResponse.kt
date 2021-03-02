package com.softvision.data.network.model

import com.softvision.data.network.base.RetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenresResponse(
    @Json(name = "genres") val genresList : List<MovieGenreResponse>
): RetrofitResponse<MovieGenreResponse> {
    override fun getContent(): List<MovieGenreResponse> {
        return genresList
    }
}
