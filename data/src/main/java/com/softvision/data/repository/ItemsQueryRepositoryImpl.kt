package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TMDBMoviesDAO
import com.softvision.data.database.dao.TMDBTVShowsDAO
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.TMDBItemDetails
//import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ItemsQueryRepositoryImpl @Inject constructor(
    private val tmdbMoviesDAO: TMDBMoviesDAO,
    private val tmdbTVShowsDAO: TMDBTVShowsDAO,
    private val resourcesApi: ApiEndpoints,
    connectivity: Connectivity
) : BaseRepository2<TMDBItemDetails, ItemDomainMapper<TMDBItemDetails>>(connectivity),
    ItemsRepository<String, TMDBItemDetails, Int> {

    override fun getData(query: String, page: Int): Single<List<TMDBItemDetails>> {

//        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = resourcesApi.searchTMDBItems(query = query, page = page).subscribeOn(Schedulers.io())

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        fetchFromCacheAction1 = { tmdbMoviesDAO.loadItemsByTitle(query) },
                        fetchFromCacheAction2 = { tmdbTVShowsDAO.loadItemsByTitle(query) }
                     ).map { it }
//                    .getData(
//                        cacheAction = {  entities -> insertItems(type, entities) },
//                        type
//                    )
            },
            dbDataProvider1 = { tmdbMoviesDAO.loadItemsByTitle(query).map { it } },
            dbDataProvider2 = { tmdbTVShowsDAO.loadItemsByTitle(query).map { it } }
        ).subscribeOn(Schedulers.io())
    }

//    private fun loadItemsByQueryValue(query: String): Single<List<TMDBMovieEntity>> {
////        return tmdbMoviesDAO.loadItemsByTitle(query)
////            .concatWith {
////                tmdbTVShowsDAO.loadItemsByTitle(query)
////            }
////            .singleOrError()
////            .subscribeOn(Schedulers.io())
//
//
//        val movies= tmdbMoviesDAO.loadItemsByTitle(query)
//        val tvShows = tmdbTVShowsDAO.loadItemsByTitle(query)
//
//        return Single.just(movies + tvShows)
//            .subscribeOn(Schedulers.io())
//    }
}