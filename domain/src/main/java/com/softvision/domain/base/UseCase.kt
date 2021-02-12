package com.softvision.domain.base

sealed class UseCase {
    object TrendingMovies: UseCase()
    object TrendingTVShows: UseCase()
    object ComingSoonMovies : UseCase()
    object ComingSoonTVShows : UseCase()
    object PopularMovies : UseCase()
    object PopularTVShows: UseCase()
}
