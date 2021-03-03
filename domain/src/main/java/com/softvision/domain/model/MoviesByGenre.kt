package com.softvision.domain.model

data class MoviesByGenre(
    val genre: Genre,
    val movies: List<BaseItemDetails>
)
