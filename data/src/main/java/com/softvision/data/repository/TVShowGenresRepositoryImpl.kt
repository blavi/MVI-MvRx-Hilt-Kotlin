package com.softvision.data.repository

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TVShowGenresDAO
import com.softvision.data.database.model.BaseItemEntity
import com.softvision.data.database.model.TvShowGenreEntity
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.getData
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.repository.GenresRepository
import io.reactivex.Single
import javax.inject.Inject

class TVShowGenresRepositoryImpl @Inject constructor(private val genresDAO: TVShowGenresDAO,
                                                     private val resourcesApi: ApiEndpoints,
                                                     connectivity: Connectivity
): BaseRepository<BaseItemDetails, ItemDomainMapper<BaseItemDetails>>(connectivity),
    GenresRepository<BaseItemDetails> {

    override fun getData(): Single<List<BaseItemDetails>> {

        val apiDataProviderVal = resourcesApi.fetchTVShowGenres()

        return fetchData(
            apiDataProvider = {
                apiDataProviderVal
                    .getData(
                        cacheAction = {  entities -> insertItems(entities) },
                        fetchFromCacheAction = { loadAllItems() }
                    )
            },
            dbDataProvider = { loadAllItems().map { it } }
        )
    }

    private fun insertItems(items: List<BaseItemEntity>) {
        (items as? List<TvShowGenreEntity>)?.let {
            genresDAO.insertItems(it)
        }
    }

    private fun loadAllItems(): Single<List<BaseItemEntity>> {
        return genresDAO
            .loadAllItems()
            .map { it }
    }
}