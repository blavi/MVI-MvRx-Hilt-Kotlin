package com.softvision.data.network.model

import com.softvision.data.network.base.RetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowGenresResponse(
    @Json(name = "genres") val genresList : List<TvShowGenreResponse>
): RetrofitResponse<TvShowGenreResponse> {
    override fun getContent(): List<TvShowGenreResponse> {
        return genresList
    }
}
