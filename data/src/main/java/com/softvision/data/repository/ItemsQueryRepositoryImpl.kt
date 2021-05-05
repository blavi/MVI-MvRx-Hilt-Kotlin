package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ItemsQueryRepositoryImpl @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val tvShowsDAO: TVShowsDAO,
    private val resourcesApi: ApiEndpoints,
    connectivity: Connectivity
) : BaseRepository<BaseItemDetails, ItemDomainMapper<BaseItemDetails>>(connectivity),
    ItemsRepository<String, BaseItemDetails, Int> {

    override fun getData(query: String, page: Int): Single<List<BaseItemDetails>> {

        val apiDataProviderVal = resourcesApi.searchTMDBItems(query = query, page = page)

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        fetchFromCacheAction1 = { moviesDAO.queryItemsByTitle(query) },
                        fetchFromCacheAction2 = { tvShowsDAO.queryItemsByTitle(query) }
                     )
            },
            dbDataProvider1 = { moviesDAO.queryItemsByTitle(query).map { it } },
            dbDataProvider2 = { tvShowsDAO.queryItemsByTitle(query).map { it } }
        )
    }

}