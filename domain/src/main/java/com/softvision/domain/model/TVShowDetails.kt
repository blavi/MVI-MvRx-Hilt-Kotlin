package com.softvision.domain.model

import android.os.Parcelable
import com.softvision.domain.model.base.ItemDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVShowDetails (
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
): Parcelable, ItemDetails()


//@Parcelize
//data class TMDBItemDetails (
//    val id : Int,
//    val adult : Boolean?,
//    val backdrop_path : String?,
//    val genre_ids : List<Int>,
//    val origin_country: List<String>?,
//    val original_language : String,
//    val original_title : String,
//    val overview : String,
//    val popularity : Double,
//    val poster_path : String?,
//    val release_date : String,
//    val title : String,
//    val video : Boolean?,
//    val vote_average : Double,
//    val vote_count : Int,
//    val media_type : String?,
//    val categories : List<String>
//): Parcelable