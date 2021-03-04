package com.softvision.domain.model

data class MoviesByGenre(
    val genreDetails: GenreDetails,
    val movies: List<BaseItemDetails>
)
