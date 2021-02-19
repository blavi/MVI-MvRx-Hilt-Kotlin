package com.softvision.data.network.model

import com.softvision.data.network.base.TMDBRetrofitResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TMDBMovieGenresResponse(
    @Json(name = "genres") val genresList : List<TMDBMovieGenreResponse>
): TMDBRetrofitResponse<TMDBMovieGenreResponse> {
    override fun getContent(): List<TMDBMovieGenreResponse> {
        return genresList
    }
}
