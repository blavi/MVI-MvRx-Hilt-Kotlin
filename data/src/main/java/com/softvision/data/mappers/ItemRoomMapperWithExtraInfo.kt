package com.softvision.data.mappers

interface ItemRoomMapperWithExtraInfo<out E : Any?, C : Any> {
    fun mapToRoomEntity(categories: List<C> = emptyList()): E
}