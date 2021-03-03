package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.BaseItemDetails
//import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ItemsQueryRepositoryImpl @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val tvShowsDAO: TVShowsDAO,
    private val resourcesApi: ApiEndpoints,
    connectivity: Connectivity
) : BaseRepository2<BaseItemDetails, ItemDomainMapper<BaseItemDetails>>(connectivity),
    ItemsRepository<String, BaseItemDetails, Int> {

    override fun getData(query: String, page: Int): Single<List<BaseItemDetails>> {

//        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = resourcesApi.searchTMDBItems(query = query, page = page).subscribeOn(Schedulers.io())

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        fetchFromCacheAction1 = { moviesDAO.loadItemsByTitle(query) },
                        fetchFromCacheAction2 = { tvShowsDAO.loadItemsByTitle(query) }
                     )
//                    .map { it }
//                    .getData(
//                        cacheAction = {  entities -> insertItems(type, entities) },
//                        type
//                    )
            },
            dbDataProvider1 = { moviesDAO.loadItemsByTitle(query).map { it } },
            dbDataProvider2 = { tvShowsDAO.loadItemsByTitle(query).map { it } }
        ).subscribeOn(Schedulers.io())
    }

}