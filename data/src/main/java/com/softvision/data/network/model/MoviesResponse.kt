package com.softvision.data.network.model

import com.softvision.data.network.base.RetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse (
    @Json(name = "page") val page : Int,
    @Json(name = "results") val itemResponses : List<MovieResponse>,
    @Json(name = "total_pages") val totalPages : Int,
    @Json(name = "total_results") val totalResults : Int
): RetrofitResponse<MovieResponse> {
    override fun getContent(): List<MovieResponse> {
        return itemResponses
    }
}