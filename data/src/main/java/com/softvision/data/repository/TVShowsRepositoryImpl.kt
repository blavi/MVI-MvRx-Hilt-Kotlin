package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TVShowsDAO
import com.softvision.data.database.model.PartialTVShowEntity
import com.softvision.data.database.model.base.ItemEntity
import com.softvision.data.database.model.TVShowEntity
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.DataType
import com.softvision.data.network.base.getData
import com.softvision.domain.model.base.ItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TVShowsRepositoryImpl @Inject constructor(private val tmdbTVShowsDAO: TVShowsDAO,
                                                private val resourcesApi: ApiEndpoints,
                                                connectivity: Connectivity
): BaseRepository<ItemDetails, ItemDomainMapper<ItemDetails>>(connectivity), ItemsRepository<String, ItemDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<ItemDetails>> {

//        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = when (type) {
            DataType.TRENDING_TV_SHOWS -> resourcesApi.fetchTrendingTVShows(page = page).subscribeOn(Schedulers.io())
            DataType.POPULAR_TV_SHOWS -> resourcesApi.fetchPopularTVShows(page = page).subscribeOn(Schedulers.io())
            DataType.COMING_SOON_TV_SHOWS -> resourcesApi.fetchComingSoonTVShows(page = page).subscribeOn(Schedulers.io())
            else -> resourcesApi.fetchTVShowsByGenre(genre = type, page = page).subscribeOn(Schedulers.io())
        }

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        cacheAction = {  entities -> insertItems(type, entities) },
                        fetchFromCacheAction = { loadItemsByCategory(type) },
                        type
                    )
//                    .getData(
//                        cacheAction = {  entities -> insertItems(type, entities) },
//                        type
//                    )
            },
            dbDataProvider = { loadItemsByCategory(type).map { it } }
        ).subscribeOn(Schedulers.io())
    }

    private fun insertItems(type: String, items: List<ItemEntity>) {
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

    private fun loadItemsByCategory(type: String): Single<List<ItemEntity>> {
        return tmdbTVShowsDAO
            .loadItemsByCategory(type)
            .map { it as List<ItemEntity> }
            .subscribeOn(Schedulers.io())
    }
}
