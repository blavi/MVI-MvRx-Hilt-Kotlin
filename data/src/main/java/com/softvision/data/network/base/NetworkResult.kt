package com.softvision.data.network.base

import com.softvision.data.mappers.*
import io.reactivex.Single

/**
 * Cache movie, tv shows items after fetching it from the API, or retrieve from cache
 */

inline fun <V : RetrofitResponse<R>, R : ItemRoomMapperWithExtraInfo<E, C>, E : ItemDomainMapper<D>, D : Any, C : Any> Single<V>.getData(
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
            fetchFromCacheAction()
                .filter { it.isNotEmpty() }
                .toSingle()
                .map { cachedEntities ->
                    dbToDomain(cachedEntities)
                }
                .onErrorResumeNext {
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
inline fun <V : RetrofitResponse<R>, R : ItemRoomMapperWithExtraInfo<E, C>, E : ItemDomainMapper<D>, D : Any, C : Any> Single<V>.getData(
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
            Single.error(it)
        }
}

/**
 * Retrieve genre items from API
 */
inline fun <V : RetrofitResponse<R>, R : ItemRoomMapper<E>, E : ItemDomainMapper<D>, D : Any> Single<V>.getData(
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
            Single.error(it)
        }
}

/**
 * Cache genre items after fetching it from the API, or retrieve from cache
 */
inline fun <V : RetrofitResponse<R>, R : ItemRoomMapper<E>, E : ItemDomainMapper<D>, D : Any> Single<V>.getData(
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
            fetchFromCacheAction()
                .filter { it.isNotEmpty() }
                .toSingle()
                .map { cachedEntities ->
                    dbToDomain(cachedEntities)
                }
                .onErrorResumeNext {
                    Single.error(it)
                }
        }
}

/**
 * Cache movie, tv shows items after fetching it from the API, or retrieve from cache
 */
//
inline fun <V : RetrofitResponse<R>, R : ItemDomainMapper<D>, E1 : ItemDomainMapper<D1>, E2 : ItemDomainMapper<D2>, D1: D, D2: D, D: Any> Single<V>.getData(
    crossinline fetchFromCacheAction1: () -> Single<List<E1>>,
    crossinline fetchFromCacheAction2: () -> Single<List<E2>>
): Single<List<D>> {

    return this
        .map {
            apiToDomain(it.getContent())
        }
        .onErrorResumeNext { _ ->
            val val1 = fetchFromCacheAction1()
                .filter { it.isNotEmpty() }
                .toSingle()
                .map { cachedEntities ->
                    dbToDomain(cachedEntities)
                }
                .onErrorResumeNext {
                    Single.error(it)
                }

            val val2 = fetchFromCacheAction2()
                .filter { it.isNotEmpty() }
                .toSingle()
                .map { cachedEntities ->
                    dbToDomain(cachedEntities)
                }
                .onErrorResumeNext {
                    Single.error(it)
                }

            val1.zipWith(val2, { l1, l2 -> l1 + l2 })

        }
}