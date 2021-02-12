package com.softvision.data.network.base

import android.util.Log
import com.softvision.domain.model.HttpError
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

interface TMDBRetrofitResponse<T : Any> {
    fun getContent(): List<T>
}

interface DomainMapper<D : Any> {
    fun mapToDomainModel(): D
}

interface RoomMapper<out E : Any, C : Any> {
    fun mapToRoomEntity(categories: List<C>): E
}

/**
 * Cache data after fetching it from the API, or retrieve from cache
 */

inline fun <V : TMDBRetrofitResponse<R>, R : RoomMapper<E, C>, E : DomainMapper<D>, D : Any, C : Any> Single<V>.getData(
        crossinline cacheAction: (List<E>) -> Unit,
        crossinline fetchFromCacheAction: () -> List<E>,
        category: C
): Single<List<D>> {

        return this
            .map {
                apiToDB(it.getContent(), category)
            }
            .doOnSuccess {
                cacheAction(it)
            }
            .map {
                dbToDomain(it)
            }
            .onErrorReturn {
                Log.i("Explore State", it.localizedMessage!!)
                val cachedEntities = fetchFromCacheAction()
                dbToDomain(cachedEntities)
            }

//        return this
//            .map {
//                var entities = emptyList<E>()
//                it.getContent().forEach { entity ->
//                    entities = entities.plus(entity.mapToRoomEntity(listOf(category)))
//                }
//                cacheAction(entities)
//
//                entities.forEach { entity ->
//                    domainEntities = domainEntities.plus(entity.mapToDomainModel())
//                }
//
//                domainEntities
//            }
//            .onErrorReturn {
//                val cachedEntities = fetchFromCacheAction()
//
//                cachedEntities.forEach { entity ->
//                    domainEntities = domainEntities.plus(entity.mapToDomainModel())
//                }
//
//                domainEntities
//            }

}

fun <R : RoomMapper<E, C>, E : DomainMapper<D>, D : Any, C : Any> apiToDB(list: List<R>, category: C): List<E> {
    return list.map {
        it.mapToRoomEntity(listOf(category))
    }
}

fun <E : DomainMapper<D>, D : Any> dbToDomain(list: List<E>): List<D> {
    return list.map {
        it.mapToDomainModel()
    }
}

/**
 * Retrieve data from API
 */
inline fun <V : TMDBRetrofitResponse<R>, R : RoomMapper<E, C>, E : DomainMapper<D>, D : Any, C : Any> Single<V>.getData(
    crossinline cacheAction: (List<E>) -> Unit,
    category: C
): Single<List<D>> {
    return this
        .map{
            apiToDB(it.getContent(), category)
        }
        .doOnSuccess {
            cacheAction(it)
        }
        .map {
            dbToDomain(it)
        }
        .onErrorResumeNext {
            Timber.i("Explore State %s", it.localizedMessage)
            Single.error(it)
        }
}
