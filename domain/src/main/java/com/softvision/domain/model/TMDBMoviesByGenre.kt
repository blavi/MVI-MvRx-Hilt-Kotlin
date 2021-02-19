package com.softvision.domain.model

data class TMDBMoviesByGenre(
    val genre: TMDBGenre,
    val movies: List<TMDBMovieDetails>
)
