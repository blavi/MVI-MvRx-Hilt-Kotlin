package com.softvision.data.mappers

interface GenreRoomMapper<out E : Any> {
    fun mapToRoomEntity(): E
}