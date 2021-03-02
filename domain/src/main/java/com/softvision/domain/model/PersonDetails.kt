package com.softvision.domain.model

import android.os.Parcelable
import com.softvision.domain.model.base.ItemDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonDetails (
    val id : Int,
    val profile_path : String?,
    val adult : Boolean?,
    val known_for : List<ItemDetails>,
    val known_for_department : String,
    val name : String,
    val gender : Int,
    val popularity: Double
): Parcelable, ItemDetails()