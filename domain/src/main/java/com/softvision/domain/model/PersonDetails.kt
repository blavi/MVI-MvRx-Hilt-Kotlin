package com.softvision.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonDetails (
    val id : Int,
    val profilePath : String?,
    val adult : Boolean?,
    val knownFor : List<BaseItemDetails>,
    val knownForDepartment : String,
    val name : String,
    val gender : Int,
    val popularity: Double?
): Parcelable, BaseItemDetails()