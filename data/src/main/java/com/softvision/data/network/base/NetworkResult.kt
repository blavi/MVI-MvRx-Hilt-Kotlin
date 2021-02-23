package com.softvision.data.network.base

import com.softvision.data.mappers.*
import io.reactivex.Single
import timber.log.Timber

/**
 * Cache movie, tv shows items after fetching it from the API, or retrieve from cache
 */

inline fun <V : TMDBRetrofitResponse<R>, R : ItemRoomMapper<E, C>, E : ItemDomainMapper<D>, D : Any, C : Any> Single<V>.getData(
    crossinline cacheAction: (List<E>) -> Unit,
    crossinline fetchFromCacheAction: () -> Single<List<E>>,
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
        .onErrorResumeNext { error ->
            Timber.i("Explore State - network error - %s", error.localizedMessage)
            fetchFromCacheAction()
                .filter { it.isNotEmpty() }
                .toSingle()
                .map { cachedEntities ->
                    dbToDomain(cachedEntities)
                }
                .onErrorResumeNext {
                    Timber.i("Explore State - repo error - %s", error.localizedMessage)
                    Single.error(it)
                }
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

/**
 * Retrieve movie, tv shows items from API
 */
inline fun <V : TMDBRetrofitResponse<R>, R : ItemRoomMapper<E, C>, E : ItemDomainMapper<D>, D : Any, C : Any> Single<V>.getData(
    crossinline cacheAction: (List<E>) -> Unit,
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
        .onErrorResumeNext {
            Timber.i("Explore State %s", it.localizedMessage)
            Single.error(it)
        }
}

/**
 * Retrieve genre items from API
 */
inline fun <V : TMDBRetrofitResponse<R>, R : GenreRoomMapper<E>, E : ItemDomainMapper<D>, D : Any> Single<V>.getData(
    crossinline cacheAction: (List<E>) -> Unit
): Single<List<D>> {
    return this
        .map {
            apiToDB(it.getContent())
        }
        .doOnSuccess {
            cacheAction(it)
        }
        .map {
            dbToDomain(it)
        }
        .onErrorResumeNext {
            Timber.i("Explore State - network error - %s", it.localizedMessage)
            Single.error(it)
        }
}

/**
 * Cache genre items after fetching it from the API, or retrieve from cache
 */
inline fun <V : TMDBRetrofitResponse<R>, R : GenreRoomMapper<E>, E : ItemDomainMapper<D>, D : Any> Single<V>.getData(
    crossinline cacheAction: (List<E>) -> Unit,
    crossinline fetchFromCacheAction: () -> Single<List<E>>,
): Single<List<D>> {
    return this
        .map {
            apiToDB(it.getContent())
        }
        .doOnSuccess {
            cacheAction(it)
        }
        .map {
            dbToDomain(it)
        }
        .onErrorResumeNext { error ->
            Timber.i("Explore State - network error - %s", error.localizedMessage)
            fetchFromCacheAction()
                .filter { it.isNotEmpty() }
                .toSingle()
                .map { cachedEntities ->
                    dbToDomain(cachedEntities)
                }
                .onErrorResumeNext {
                    Timber.i("Explore State - repo error - %s", error.localizedMessage)
                    Single.error(it)
                }
        }
}
