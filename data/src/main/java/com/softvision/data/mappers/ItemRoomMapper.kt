package com.softvision.data.mappers

interface ItemRoomMapper<out E : Any?> {
    fun mapToRoomEntity(): E
}