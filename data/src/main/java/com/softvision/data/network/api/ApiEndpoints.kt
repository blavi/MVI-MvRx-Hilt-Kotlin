package com.softvision.data.network.api

import com.softvision.data.BuildConfig
import com.softvision.data.network.base.*
import com.softvision.data.network.base.NetworkConstants.Companion.API_KEY
import com.softvision.data.network.base.NetworkConstants.Companion.INCLUDE_ADULT
import com.softvision.data.network.base.NetworkConstants.Companion.MEDIA_TYPE
import com.softvision.data.network.base.NetworkConstants.Companion.MOVIE_RELEASE_YEAR
import com.softvision.data.network.base.NetworkConstants.Companion.PAGE
import com.softvision.data.network.base.NetworkConstants.Companion.QUERY
import com.softvision.data.network.base.NetworkConstants.Companion.SORT_BY
import com.softvision.data.network.base.NetworkConstants.Companion.TIME_WINDOW
import com.softvision.data.network.base.NetworkConstants.Companion.TV_SHOW_RELEASE_YEAR
import com.softvision.data.network.base.NetworkConstants.Companion.WITH_GENRES
import com.softvision.data.network.model.MovieGenresResponse
import com.softvision.data.network.model.MoviesResponse
import com.softvision.data.network.model.MultiItemsResponse
import com.softvision.data.network.model.TVShowsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoints {

    @GET("trending/{media_type}/{time_window}")
    fun fetchTrendingMovies(
        @Path(MEDIA_TYPE) mediaType: String = NetworkConstants.MEDIA_TYPE_MOVIES,
        @Path(TIME_WINDOW) timeWindow: String = NetworkConstants.TIME_WINDOW_DAY,
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(PAGE) page: Int,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): Single<MoviesResponse>

    @GET("trending/{media_type}/{time_window}")
    fun fetchTrendingTVShows(
        @Path(MEDIA_TYPE) mediaType: String = NetworkConstants.MEDIA_TYPE_TV_SHOWS,
        @Path(TIME_WINDOW) timeWindow: String = NetworkConstants.TIME_WINDOW_DAY,
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(PAGE) page: Int,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false
    ): Single<TVShowsResponse>

    @GET("discover/movie")
    fun fetchPopularMovies(
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_POPULAR_DESCENDING,
        @Query(PAGE) page: Int
    ): Single<MoviesResponse>

    @GET("discover/tv")
    fun fetchPopularTVShows(
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_POPULAR_DESCENDING,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(PAGE) page: Int
    ): Single<TVShowsResponse>

    @GET("discover/movie")
    fun fetchComingSoonMovies(
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(MOVIE_RELEASE_YEAR) releaseYear: String = "2021",
        @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_COMING_SOON_MOVIES,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(PAGE) page: Int
    ): Single<MoviesResponse>

    @GET("discover/tv")
    fun fetchComingSoonTVShows(
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(TV_SHOW_RELEASE_YEAR) year: String = "2021",
        @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_COMING_SOON_TV_SHOWS,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(PAGE) page: Int
    ): Single<TVShowsResponse>


    @GET("discover/movie")
    fun fetchMoviesByGenre(
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(WITH_GENRES) genre: String,
        @Query(PAGE) page: Int
    ): Single<MoviesResponse>

    @GET("discover/movie")
    fun fetchTVShowsByGenre(
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(WITH_GENRES) genre: String,
        @Query(PAGE) page: Int
    ): Single<TVShowsResponse>

    @GET("genre/movie/list")
    fun fetchMovieGenres(@Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY): Single<MovieGenresResponse>

    @GET("genre/tv/list")
    fun fetchTVShowGenres(@Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY): Single<MovieGenresResponse>

    @GET("search/multi")
    fun searchTMDBItems(
        @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int
    ): Single<MultiItemsResponse>
}