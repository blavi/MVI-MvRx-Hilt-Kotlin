package com.softvision.data.repository

import android.content.Context
import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TMDBMovieGenresDAO
import com.softvision.data.database.model.TMDBMovieGenreEntity
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.TMDBGenre
import com.softvision.domain.repository.GenresRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MovieGenresRepositoryImpl @Inject constructor(private val genresDAO: TMDBMovieGenresDAO,
                                                    private val resourcesApi: ApiEndpoints,
                                                    connectivity: Connectivity
): BaseRepository<TMDBGenre, TMDBMovieGenreEntity>(connectivity), GenresRepository<TMDBGenre> {

    override fun getData(): Single<List<TMDBGenre>> {

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

    private fun insertItems(items: List<TMDBMovieGenreEntity>) {
        genresDAO.insertItems(items)
    }

    private fun loadAllItems(): Single<List<TMDBMovieGenreEntity>> {
        return genresDAO.loadAllItems().subscribeOn(Schedulers.io())
    }
}
