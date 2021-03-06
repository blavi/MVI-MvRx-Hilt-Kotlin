package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.database.model.PartialTVShowEntity
import com.softvision.data.database.model.BaseItemEntity
import com.softvision.data.database.model.TVShowEntity
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.TVShowDataType
import com.softvision.data.network.base.getData
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TVShowsRepositoryImpl @Inject constructor(private val tmdbTVShowsDAO: TVShowsDAO,
                                                private val resourcesApi: ApiEndpoints,
                                                connectivity: Connectivity
): BaseRepository<BaseItemDetails, ItemDomainMapper<BaseItemDetails>>(connectivity), ItemsRepository<String, BaseItemDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<BaseItemDetails>> {

        val apiDataProviderVal = when (type) {
            TVShowDataType.TRENDING_TV_SHOWS -> resourcesApi.fetchTrendingTVShows(page = page)
            TVShowDataType.POPULAR_TV_SHOWS -> resourcesApi.fetchPopularTVShows(page = page)
            TVShowDataType.COMING_SOON_TV_SHOWS -> resourcesApi.fetchComingSoonTVShows(page = page)
            else -> null
        }

        return if (apiDataProviderVal == null) {
            Single.just(emptyList())
        } else {
            fetchData(
                apiDataProvider = {
                    apiDataProviderVal
                        .getData(
                            cacheAction = {  entities -> insertItems(type, entities) },
                            fetchFromCacheAction = { loadItemsByCategory(type) },
                            type
                        )
                },
                dbDataProvider = { loadItemsByCategory(type).map { it } }
            )
        }
    }

    private fun insertItems(type: String, items: List<BaseItemEntity>) {
        items.forEach { itemEntity ->
            (itemEntity as TVShowEntity).apply {
                val foundItem = tmdbTVShowsDAO.getItem(id)
                if (foundItem != null) {
                    if (!foundItem.categories.contains(type)) {
                        tmdbTVShowsDAO.update(PartialTVShowEntity(id, foundItem.categories.plus(type)))
                    }
                } else {
                    tmdbTVShowsDAO.insertItem(this)
                }
            }
        }
    }

    private fun loadItemsByCategory(type: String): Single<List<BaseItemEntity>> {
        return tmdbTVShowsDAO
            .loadItemsByCategory(type)
            .map { it as List<BaseItemEntity> }
    }
}
