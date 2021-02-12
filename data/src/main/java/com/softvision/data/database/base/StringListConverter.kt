package com.softvision.data.database.base

import androidx.room.TypeConverter


class StringListConverter {
    @TypeConverter
    fun stringToList(data: String): List<String> {
        var listVal = emptyList<String>()
        data.split(",").forEach {
            listVal = listVal.plus(it)
        }
        return listVal
    }

    @TypeConverter
    fun listToString(someObjects: List<String>): String {
        var stringVal = ""
        someObjects.forEach {
            stringVal += "$it,"
        }
        return if (stringVal.isNotEmpty()) {
            stringVal.substring(0, stringVal.length - 1)
        } else {
            stringVal
        }
    }
}