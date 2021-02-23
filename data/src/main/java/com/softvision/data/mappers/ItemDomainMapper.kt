package com.softvision.data.mappers

interface ItemDomainMapper<D : Any> {
    fun mapToDomainModel(): D
}