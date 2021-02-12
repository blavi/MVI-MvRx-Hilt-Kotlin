package com.softvision.data.database.base

import androidx.room.TypeConverter

class IntListConverter {
    @TypeConverter
    fun stringToList(data: String): List<Int> {
        var listVal = emptyList<Int>()
        data.split(",").forEach {
            try {
                listVal = listVal.plus(it.toInt())
            } catch (e: Exception) {
                data
            }
        }
        return listVal
    }

    @TypeConverter
    fun listToString(someObjects: List<Int>): String {
        var stringVal = ""
        someObjects.forEach {
            stringVal += "${it},"
        }
        return if (stringVal.isNotEmpty()){
            stringVal.substring(0, stringVal.length - 1)
        } else {
            stringVal
        }
    }
}