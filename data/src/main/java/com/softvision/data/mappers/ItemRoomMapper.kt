package com.softvision.data.mappers

interface ItemRoomMapper<out E : Any?, C : Any> {
    fun mapToRoomEntity(categories: List<C> = emptyList()): E
}