package com.softvision.data.network.model

import com.softvision.data.network.base.RetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultiItemsResponse (
    @Json(name = "page") val page : Int,
    @Json(name = "results") val results : List<Item>,
    @Json(name = "total_pages") val total_pages : Int,
    @Json(name = "total_results") val total_results : Int
): RetrofitResponse<Item> {
    override fun getContent(): List<Item> {
        return results
    }
}