package com.softvision.data.network.model

import com.softvision.data.network.base.TMDBRetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBMoviesResponse (
    @Json(name = "page") val page : Int,
    @Json(name = "results") val tmdbItemResponses : List<TMDBMovieResponse>,
    @Json(name = "total_pages") val total_pages : Int,
    @Json(name = "total_results") val total_results : Int
): TMDBRetrofitResponse<TMDBMovieResponse> {
    override fun getContent(): List<TMDBMovieResponse> {
        return tmdbItemResponses
    }
}