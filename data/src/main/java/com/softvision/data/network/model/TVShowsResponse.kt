package com.softvision.data.network.model

import com.softvision.data.network.base.RetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVShowsResponse (
    @Json(name = "page") val page : Int,
    @Json(name = "results") val itemRespons : List<TVShowResponse>,
    @Json(name = "total_pages") val total_pages : Int,
    @Json(name = "total_results") val total_results : Int
): RetrofitResponse<TVShowResponse> {
    override fun getContent(): List<TVShowResponse> {
        return itemRespons
    }
}

//@JsonClass(generateAdapter = true)
//data class TMDBTVShowsResponse (
//    @Json(name = "page") val page : Int,
//    @Json(name = "results") val tmdbItemResponses : List<Item>,
//    @Json(name = "total_pages") val total_pages : Int,
//    @Json(name = "total_results") val total_results : Int
//): TMDBRetrofitResponse<Item> {
//    override fun getContent(): List<Item> {
//        return tmdbItemResponses
//    }
//}