package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.mappers.ItemDomainMapper
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject


open class BaseRepository2<D: Any, E: ItemDomainMapper<D>> @Inject constructor(val connectivity: Connectivity){

    /**
     * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
     */
    protected fun fetchData(apiDataProvider: () -> Single<List<D>>,
                            dbDataProvider1: () -> Single<List<E>>,
                            dbDataProvider2: () -> Single<List<E>>): Single<List<D>> {
        return if (connectivity.hasNetworkAccess()) {
            apiDataProvider()
        } else {
            fetchDataFromDB(dbDataProvider1)
                .zipWith(
                    fetchDataFromDB(dbDataProvider2),
                    { l1, l2 -> l1 + l2 }
                )
        }
    }

    private fun <E: ItemDomainMapper<D>> fetchDataFromDB( provider: () ->  Single<List<E>>): Single<List<D>> {
        return provider()
            .filter {
                it.isNotEmpty()
            }
            .toSingle()
            .map { list ->
                list.map {
                    it.mapToDomainModel()
                }
            }
            .onErrorResumeNext {
                Timber.i("XYZ -  baserepo2  repo error - %s", it.localizedMessage)
                Single.error(it)
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