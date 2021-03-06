package com.softvision.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softvision.data.database.base.TMDB_PERSONS
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.PersonDetails
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = TMDB_PERSONS)
data class PersonEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int,

    @ColumnInfo(name = "adult")
    val adult : Boolean? = true,

    @ColumnInfo(name = "popularity")
    val popularity : Double?,

    @ColumnInfo(name = "profile_path")
    val profilePath : String?,

    @ColumnInfo(name = "known_for")
    val knownFor : List<BaseItemDetails>,

    @ColumnInfo(name = "known_for_department")
    val knownForDepartment : String,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "gender")
    val gender : Int,

    ): BaseItemEntity() {
    override fun mapToDomainModel(): PersonDetails {
        return PersonDetails(id, profilePath, adult, knownFor, knownForDepartment, name, gender, popularity)
    }

}