package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.network.base.DomainMapper
import io.reactivex.Single
import javax.inject.Inject

abstract class BaseRepository<D : Any, E : DomainMapper<D>> {
    @Inject
    lateinit var connectivity: Connectivity

    /**
     * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
     */
    protected fun fetchData(apiDataProvider: () -> Single<List<D>>, dbDataProvider: () -> List<E>): Single<List<D>> {
        return if (connectivity.hasNetworkAccess()) {
            apiDataProvider()
        } else {
            val dbResult = dbDataProvider()
            var mappedDbResult = emptyList<D>()

            if (dbResult != null) {
                dbResult.forEach {
                    mappedDbResult = mappedDbResult.plus(it.mapToDomainModel())
                }
            }

            Single.just(mappedDbResult)
        }
    }

//    /**
//     * Use this when communicating only with the api service
//     */
//    protected suspend fun fetchData(apiDataProvider: () -> Result<T>): Result<T> {
//        return if (connectivity.hasNetworkAccess()) {
//            withContext(contextProvider.io) {
//                apiDataProvider()
//            }
//        } else {
//            Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
//        }
//    }
}