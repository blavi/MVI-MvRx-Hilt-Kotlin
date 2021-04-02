package com.softvision.data.network.model

import com.softvision.data.network.base.RetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVShowsResponse (
    @Json(name = "page") val page : Int,
    @Json(name = "results") val itemResponse : List<TVShowResponse>,
    @Json(name = "total_pages") val totalPages : Int,
    @Json(name = "total_results") val totalResults : Int
): RetrofitResponse<TVShowResponse> {
    override fun getContent(): List<TVShowResponse> {
        return itemResponse
    }
}