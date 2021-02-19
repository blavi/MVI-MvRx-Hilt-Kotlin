package com.softvision.data.repository

import com.softvision.data.database.dao.TMDBMoviesDAO
import com.softvision.data.database.model.PartialTMDBMovieEntity
import com.softvision.data.database.model.TMDBMovieEntity
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.DataType
import com.softvision.data.network.base.getData
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.repository.ItemsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val tmdbMoviesDAO: TMDBMoviesDAO,
                                               private val resourcesApi: ApiEndpoints):
    BaseRepository<TMDBMovieDetails, TMDBMovieEntity>(), ItemsRepository<String, TMDBMovieDetails, Int> {

    override fun getData(type: String, page: Int): Single<List<TMDBMovieDetails>> {

        Timber.i("Explore State: type: %s, page: %s", type, page)
        val apiDataProviderVal = when (type) {
            DataType.TRENDING_MOVIES -> resourcesApi.fetchTrendingMovies(page = page).subscribeOn(Schedulers.io())
            DataType.POPULAR_MOVIES -> resourcesApi.fetchPopularMovies(page = page).subscribeOn(Schedulers.io())
            DataType.COMING_SOON_MOVIES -> resourcesApi.fetchComingSoonMovies(page = page).subscribeOn(Schedulers.io())
            else -> resourcesApi.fetchMoviesByGenre(genre = type, page = page).subscribeOn(Schedulers.io())
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

    private fun insertItems(type: String, items: List<TMDBMovieEntity>) {
        items.forEach {
            val foundItem = tmdbMoviesDAO.getItem(it.id)
            if (foundItem != null) {
                if (!foundItem.categories.contains(type)) {
                    tmdbMoviesDAO.update(PartialTMDBMovieEntity(it.id, foundItem.categories.plus(type)))
                }
            } else {
                tmdbMoviesDAO.insertItem(it)
            }
        }
    }

    private fun loadItemsByCategory(type: String): List<TMDBMovieEntity> {
        return tmdbMoviesDAO.loadItemsByCategory(type)
    }
}
