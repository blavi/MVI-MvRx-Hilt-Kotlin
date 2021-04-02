package com.softvision.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVShowDetails (
    val id : Int,
    val backdropPath : String?,
    val genreIds : List<Int>,
    val originCountry: List<String>,
    val originalLanguage : String,
    val originalTitle : String,
    val overview : String,
    val popularity : Double?,
    val posterPath : String?,
    val releaseDate : String,
    val title : String,
    val voteAverage : Double,
    val voteCount : Int,
    val categories : List<String>
): Parcelable, BaseItemDetails()