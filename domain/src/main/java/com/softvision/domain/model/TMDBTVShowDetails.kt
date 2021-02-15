package com.softvision.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TMDBTVShowDetails (
    val id : Int,
    val backdrop_path : String?,
    val genre_ids : List<Int>,
    val origin_country: List<String>,
    val original_language : String,
    val original_title : String,
    val overview : String,
    val popularity : Double,
    val poster_path : String?,
    val release_date : String,
    val title : String,
    val vote_average : Double,
    val vote_count : Int,
    val categories : List<String>
): Parcelable, TMDBItemDetails()