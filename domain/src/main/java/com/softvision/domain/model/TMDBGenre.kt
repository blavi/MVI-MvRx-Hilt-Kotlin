package com.softvision.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TMDBGenre(
    val id : Int,
    val name : String
): Parcelable
