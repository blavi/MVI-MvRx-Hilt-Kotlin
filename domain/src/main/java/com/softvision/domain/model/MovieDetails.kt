package com.softvision.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetails (
    val id : Int,
    val adult : Boolean?,
    val backdropPath : String?,
    val genreIds : List<Int>?,
    val originalLanguage : String,
    val originalTitle : String,
    val overview : String,
    val popularity : Double?,
    val posterPath : String?,
    val releaseDate : String,
    val title : String,
    val video : Boolean,
    val voteAverage : Double,
    val voteCount : Int,
    val mediaType : String?,
    val categories : List<String>
): Parcelable, BaseItemDetails()