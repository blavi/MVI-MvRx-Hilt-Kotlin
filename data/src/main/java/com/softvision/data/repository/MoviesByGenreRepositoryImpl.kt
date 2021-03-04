package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.model.BaseItemEntity
import com.softvision.data.database.model.MovieEntity
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MoviesByGenreRepositoryImpl @Inject constructor(
    private val tmdbMoviesDAO: MoviesDAO,
    private val resourcesApi: ApiEndpoints,
    connectivity: Connectivity
) : BaseRepository<BaseItemDetails, ItemDomainMapper<BaseItemDetails>>(connectivity),
    ItemsRepository<String, BaseItemDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<BaseItemDetails>> {

//        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = resourcesApi.fetchMoviesByGenre(genre = type, page = page)

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
            (itemEntity as MovieEntity).apply {
                tmdbMoviesDAO.insertOrIgnoreItem(this)
//                val foundItem = tmdbMoviesDAO.getItem(id)
//                if (foundItem == null) {
//                    tmdbMoviesDAO.insertOrReplaceItem(this)
//                }
            }
        }
    }

    private fun loadItemsByCategory(type: String): Single<List<BaseItemEntity>> {
        return tmdbMoviesDAO
            .loadItemsByGenre(type)
            .map { it as List<BaseItemEntity> }
    }
}
