package com.softvision.data.network.model

import com.softvision.data.network.base.TMDBRetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBResponse (
    @Json(name = "page") val page : Int,
    @Json(name = "results") val tmdbItemResponses : List<TMDBItemResponse>,
    @Json(name = "total_pages") val total_pages : Int,
    @Json(name = "total_results") val total_results : Int
): TMDBRetrofitResponse<TMDBItemResponse> {
    override fun getContent(): List<TMDBItemResponse> {
        return tmdbItemResponses
    }
}