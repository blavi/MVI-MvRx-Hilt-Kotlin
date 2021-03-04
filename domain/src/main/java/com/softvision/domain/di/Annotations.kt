package com.softvision.domain.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoviesInteractor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoviesByGenreInteractor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TvShowsInteractor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QueryInteractor



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoviesRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoviesByGenreRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TvShowsRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class QueryRepository