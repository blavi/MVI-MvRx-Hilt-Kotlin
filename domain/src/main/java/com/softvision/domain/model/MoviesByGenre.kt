package com.softvision.domain.model

import com.softvision.domain.model.base.ItemDetails

data class MoviesByGenre(
    val genre: Genre,
    val movies: List<ItemDetails>
)
