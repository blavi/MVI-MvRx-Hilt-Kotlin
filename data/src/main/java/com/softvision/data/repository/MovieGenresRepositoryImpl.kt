package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MovieGenresDAO
import com.softvision.data.database.model.MovieGenreEntity
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.Genre
import com.softvision.domain.repository.GenresRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieGenresRepositoryImpl @Inject constructor(private val genresDAO: MovieGenresDAO,
                                                    private val resourcesApi: ApiEndpoints,
                                                    connectivity: Connectivity
): BaseRepository<Genre, MovieGenreEntity>(connectivity), GenresRepository<Genre> {

    override fun getData(): Single<List<Genre>> {

        val apiDataProviderVal = resourcesApi.fetchMovieGenres()
//        Timber.i("Explore State: type: FETCH GENRES")

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        cacheAction = {  entities -> insertItems(entities) },
                        fetchFromCacheAction = { loadAllItems() }
                    )
            },
            dbDataProvider = { loadAllItems() }
        ).subscribeOn(Schedulers.io())
    }

    private fun insertItems(items: List<MovieGenreEntity>) {
        genresDAO.insertItems(items)
    }

    private fun loadAllItems(): Single<List<MovieGenreEntity>> {
        return genresDAO.loadAllItems().subscribeOn(Schedulers.io())
    }
}
