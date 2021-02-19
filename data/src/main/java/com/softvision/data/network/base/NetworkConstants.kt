package com.softvision.data.network.base


class DataType {
    companion object {
        const val TRENDING_MOVIES = "trending_movies"
        const val TRENDING_TV_SHOWS = "trending_tv_shows"
        const val POPULAR_MOVIES = "popular_movies"
        const val POPULAR_TV_SHOWS = "popular_tv_shows"
        const val COMING_SOON_MOVIES = "coming_soon_movies"
        const val COMING_SOON_TV_SHOWS = "coming_soon_tv_shows"
    }
}

class NetworkConstants {
    companion object {
        const val API_KEY = "api_key"
        const val PAGE = "page"

        const val MEDIA_TYPE = "media_type"
        const val MEDIA_TYPE_ALL = "all"
        const val MEDIA_TYPE_MOVIES = "movie"
        const val MEDIA_TYPE_TV_SHOWS = "tv"
        const val MEDIA_TYPE_PERSON = "person"

        const val TIME_WINDOW = "time_window"
        const val TIME_WINDOW_DAY = "day"
        const val TIME_WINDOW_WEEK = "week"

        const val SORT_BY = "sort_by"
        const val SORT_BY_POPULAR_DESCENDING = "popularity.desc"
        const val SORT_BY_COMMING_SOON_MOVIES = "primary_release_date.asc"
        const val SORT_BY_COMMING_SOON_TV_SHOWS = "first_air_date.asc"

        const val MOVIE_RELEASE_YEAR = "primary_release_year"
        const val TV_SHOW_RELEASE_YEAR = "first_air_date_year"

        const val WITH_GENRES = "with_genres"
    }
}

