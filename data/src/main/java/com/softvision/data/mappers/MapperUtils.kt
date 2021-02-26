package com.softvision.data.mappers

fun <R : ItemRoomMapper<E, C>, E : ItemDomainMapper<D>, D : Any, C : Any> apiToDB(list: List<R>, category: C): List<E> {
    return list.map {
        it.mapToRoomEntity(listOf(category))
    }
}

fun <R : ItemDomainMapper<D>, D : Any> apiToDomain(list: List<R>): List<D> {
    return list.map {
        it.mapToDomainModel()
    }
}

fun <R : GenreRoomMapper<E>, E : ItemDomainMapper<D>, D : Any> apiToDB(list: List<R>): List<E> {
    return list.map {
        it.mapToRoomEntity()
    }
}

fun <E : ItemDomainMapper<D>, D : Any> dbToDomain(list: List<E>): List<D> {
    return list.map {
        it.mapToDomainModel()
    }
}