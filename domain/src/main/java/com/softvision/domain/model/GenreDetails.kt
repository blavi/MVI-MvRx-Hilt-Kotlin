package com.softvision.domain.model

import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreDetails(
    val id : Int,
    val name : String
): BaseItemDetails()
