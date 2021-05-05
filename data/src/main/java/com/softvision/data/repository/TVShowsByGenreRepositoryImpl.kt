package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.database.model.BaseItemEntity
import com.softvision.data.database.model.TVShowEntity
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class TVShowsByGenreRepositoryImpl  @Inject constructor(
    private val tmdbTVShowsDAO: TVShowsDAO,
    private val resourcesApi: ApiEndpoints,
    connectivity: Connectivity
) : BaseRepository<BaseItemDetails, ItemDomainMapper<BaseItemDetails>>(connectivity),
    ItemsRepository<String, BaseItemDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<BaseItemDetails>> {

//        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = resourcesApi.fetchTVShowsByGenre(genre = type, page = page)

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        cacheAction = { entities ->
                            Timber.i("Movies: insert by genre - type: %s, page: %s", type, page)
                            insertItems(entities)
                        },
                        fetchFromCacheAction = { loadItemsByCategory(type) }
                    )
//                    .getData(
//                        cacheAction = {  entities -> insertItems(type, entities) },
//                        type
//                    )
            },
            dbDataProvider = { loadItemsByCategory(type).map { it } }
        )
    }

    private fun insertItems(items: List<BaseItemEntity>) {

        items.forEach { itemEntity ->
            (itemEntity as TVShowEntity).apply {
                tmdbTVShowsDAO.insertOrIgnoreItem(this)
            }
        }
    }

    private fun loadItemsByCategory(type: String): Single<List<BaseItemEntity>> {
        return tmdbTVShowsDAO
            .loadItemsByGenre(type)
            .map { it as List<BaseItemEntity> }
    }
}
