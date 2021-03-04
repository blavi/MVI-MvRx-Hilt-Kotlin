package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.model.PartialMovieEntity
import com.softvision.data.database.model.BaseItemEntity
import com.softvision.data.database.model.MovieEntity
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.DataType
import com.softvision.data.network.base.getData
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val tmdbMoviesDAO: MoviesDAO,
    private val resourcesApi: ApiEndpoints,
    connectivity: Connectivity
) : BaseRepository<BaseItemDetails, ItemDomainMapper<BaseItemDetails>>(connectivity),
    ItemsRepository<String, BaseItemDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<BaseItemDetails>> {

        val apiDataProviderVal = when (type) {
            DataType.TRENDING_MOVIES -> resourcesApi.fetchTrendingMovies(page = page)
            DataType.POPULAR_MOVIES -> resourcesApi.fetchPopularMovies(page = page)
            else -> resourcesApi.fetchComingSoonMovies(page = page)
//            else -> resourcesApi.fetchMoviesByGenre(genre = type, page = page)
        }

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        cacheAction = { entities ->
                            Timber.i("Movies: insert - type: %s, page: %s", type, page)
                            insertItems(type, entities)
                        },
                        fetchFromCacheAction = { loadItemsByCategory(type) },
                        type
                    )
//                    .getData(
//                        cacheAction = {  entities -> insertItems(type, entities) },
//                        type
//                    )
            },
            dbDataProvider = { loadItemsByCategory(type).map { it } }
        )
    }

    private fun insertItems(type: String, items: List<BaseItemEntity>) {
        items.forEach { itemEntity ->
            (itemEntity as MovieEntity).apply {
                val foundItem = tmdbMoviesDAO.getItem(id)
                if (foundItem != null) {
                    if (!foundItem.categories.contains(type)) {
                        tmdbMoviesDAO.update(
                            PartialMovieEntity(
                                id,
                                foundItem.categories.plus(type)
                            )
                        )
                    }
                } else {
                    tmdbMoviesDAO.insertItem(this)
                }
            }
        }
    }

    private fun loadItemsByCategory(type: String): Single<List<BaseItemEntity>> {
        return tmdbMoviesDAO
            .loadItemsByCategory(type)
            .map { it as List<BaseItemEntity> }
            .subscribeOn(Schedulers.io())
    }
}
