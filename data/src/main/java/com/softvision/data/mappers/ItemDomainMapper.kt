package com.softvision.data.mappers

interface ItemDomainMapper<out D : Any> {
    fun mapToDomainModel(): D
}