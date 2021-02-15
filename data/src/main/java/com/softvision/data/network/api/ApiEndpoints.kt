package com.softvision.data.network.api

import com.softvision.data.BuildConfig
import com.softvision.data.network.base.*
import com.softvision.data.network.base.NetworkConstants.Companion.API_KEY
import com.softvision.data.network.base.NetworkConstants.Companion.MEDIA_TYPE
import com.softvision.data.network.base.NetworkConstants.Companion.MOVIE_RELEASE_YEAR
import com.softvision.data.network.base.NetworkConstants.Companion.PAGE
import com.softvision.data.network.base.NetworkConstants.Companion.SORT_BY
import com.softvision.data.network.base.NetworkConstants.Companion.TIME_WINDOW
import com.softvision.data.network.base.NetworkConstants.Companion.TV_SHOW_RELEASE_YEAR
import com.softvision.data.network.model.TMDBMoviesResponse
import com.softvision.data.network.model.TMDBTVShowsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoints {

    @GET("trending/{media_type}/{time_window}")
    fun fetchTrendingMovies(@Path(MEDIA_TYPE) media_type: String = NetworkConstants.MEDIA_TYPE_MOVIES,
                            @Path(TIME_WINDOW) time_window: String = NetworkConstants.TIME_WINDOW_DAY,
                            @Query(PAGE) page: Int,
                            @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY): Single<TMDBMoviesResponse>

    @GET("trending/{media_type}/{time_window}")
    fun fetchTrendingTVShows(@Path(MEDIA_TYPE) media_type: String = NetworkConstants.MEDIA_TYPE_TV_SHOWS,
                            @Path(TIME_WINDOW) time_window: String = NetworkConstants.TIME_WINDOW_DAY,
                            @Query(PAGE) page: Int,
                            @Query(API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY): Single<TMDBTVShowsResponse>

    @GET("discover/movie")
    fun fetchPopularMovies(@Query(API_KEY) apiKey: String  = BuildConfig.TMDB_API_KEY,
                           @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_POPULAR_DESCENDING,
                           @Query(PAGE) page: Int): Single<TMDBMoviesResponse>

    @GET("discover/tv")
    fun fetchPopularTVShows(@Query(API_KEY) apiKey: String  = BuildConfig.TMDB_API_KEY,
                            @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_POPULAR_DESCENDING,
                            @Query(PAGE) page: Int): Single<TMDBTVShowsResponse>

    @GET("discover/movie")
    fun fetchComingSoonMovies(@Query(API_KEY) apiKey: String  = BuildConfig.TMDB_API_KEY,
                              @Query(MOVIE_RELEASE_YEAR) releaseYear: String = "2021",
                              @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_COMMING_SOON_MOVIES,
                              @Query(PAGE) page: Int): Single<TMDBMoviesResponse>

    @GET("discover/tv")
    fun fetchComingSoonTVShows(@Query(API_KEY) apiKey: String  = BuildConfig.TMDB_API_KEY,
                               @Query(TV_SHOW_RELEASE_YEAR) year: String = "2021",
                               @Query(SORT_BY) sortByValue: String = NetworkConstants.SORT_BY_COMMING_SOON_TV_SHOWS,
                               @Query(PAGE) page: Int): Single<TMDBTVShowsResponse>
}