package com.softvision.data.repository

import com.softvision.data.database.dao.TMDBItemsDAO
import com.softvision.data.database.model.PartialTMDBItemEntity
import com.softvision.data.database.model.TMDBItemEntity
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.DataType
import com.softvision.data.network.base.getData
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.repository.ResourcesRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ExplorerRepositoryImpl @Inject constructor(private val tmdbItemsDAO: TMDBItemsDAO,
                                                 private val resourcesApi: ApiEndpoints):
    BaseRepository<TMDBItemDetails, TMDBItemEntity>(), ResourcesRepository<String, TMDBItemDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<TMDBItemDetails>> {

        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = when (type) {
            DataType.TRENDING_MOVIES -> resourcesApi.fetchTrendingMovies(page = page).subscribeOn(Schedulers.io())
            DataType.POPULAR_MOVIES -> resourcesApi.fetchPopularMovies(page = page).subscribeOn(Schedulers.io())
            else -> resourcesApi.fetchComingSoonMovies(page = page).subscribeOn(Schedulers.io())
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

    private fun insertItems(type: String, items: List<TMDBItemEntity>) {
        items.forEach {
            val foundItem = tmdbItemsDAO.getItem(it.id)
            if (foundItem != null) {
                if (!foundItem.categories.contains(type)) {
                    tmdbItemsDAO.update(PartialTMDBItemEntity(it.id, foundItem.categories.plus(type)))
                }
            } else {
                tmdbItemsDAO.insertItem(it)
            }
        }
    }

    private fun loadItemsByCategory(type: String): List<TMDBItemEntity> {
        return tmdbItemsDAO.loadItemsByCategory(type)
    }
}
