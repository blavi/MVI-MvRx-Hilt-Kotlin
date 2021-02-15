package com.softvision.data.repository

import com.softvision.data.database.dao.TMDBTVShowsDAO
import com.softvision.data.database.model.PartialTMDBTVShowEntity
import com.softvision.data.database.model.TMDBTVShowEntity
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.DataType
import com.softvision.data.network.base.getData
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.repository.ResourcesRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ExplorerTVShowsRepositoryImpl @Inject constructor(private val tmdbTVShowsDAO: TMDBTVShowsDAO,
                                                       private val resourcesApi: ApiEndpoints):
    BaseRepository<TMDBTVShowDetails, TMDBTVShowEntity>(), ResourcesRepository<String, TMDBTVShowDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<TMDBTVShowDetails>> {

        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = when (type) {
            DataType.TRENDING_TV_SHOWS -> resourcesApi.fetchTrendingTVShows(page = page).subscribeOn(Schedulers.io())
            DataType.POPULAR_TV_SHOWS -> resourcesApi.fetchPopularTVShows(page = page).subscribeOn(Schedulers.io())
            else -> resourcesApi.fetchComingSoonTVShows(page = page).subscribeOn(Schedulers.io())
        }

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
//                    .getData(
//                        cacheAction = {  entities -> insertItems(type, entities) },
//                        fetchFromCacheAction = { loadItemsByCategory(type) },
//                        type
//                    )
                    .getData(
                        cacheAction = {  entities -> insertItems(type, entities) },
                        type
                    )
            },
            dbDataProvider = { loadItemsByCategory(type) }
        ).subscribeOn(Schedulers.io())
    }

    private fun insertItems(type: String, items: List<TMDBTVShowEntity>) {
        items.forEach {
            val foundItem = tmdbTVShowsDAO.getItem(it.id)
            if (foundItem != null) {
                if (!foundItem.categories.contains(type)) {
                    tmdbTVShowsDAO.update(PartialTMDBTVShowEntity(it.id, foundItem.categories.plus(type)))
                }
            } else {
                tmdbTVShowsDAO.insertItem(it)
            }
        }
    }

    private fun loadItemsByCategory(type: String): List<TMDBTVShowEntity> {
        return tmdbTVShowsDAO.loadItemsByCategory(type)
    }
}
