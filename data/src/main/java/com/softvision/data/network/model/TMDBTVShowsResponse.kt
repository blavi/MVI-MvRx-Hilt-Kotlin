package com.softvision.data.network.model

import com.softvision.data.network.base.TMDBRetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBTVShowsResponse (
    @Json(name = "page") val page : Int,
    @Json(name = "results") val tmdbItemResponses : List<TMDBTVShowResponse>,
    @Json(name = "total_pages") val total_pages : Int,
    @Json(name = "total_results") val total_results : Int
): TMDBRetrofitResponse<TMDBTVShowResponse> {
    override fun getContent(): List<TMDBTVShowResponse> {
        return tmdbItemResponses
    }
}