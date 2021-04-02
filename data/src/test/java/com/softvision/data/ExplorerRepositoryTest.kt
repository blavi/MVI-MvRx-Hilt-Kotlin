package com.softvision.data

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.MoviesDAO
import com.softvision.data.database.model.MovieEntity
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.MovieDataType
import com.softvision.data.network.model.MovieResponse
import com.softvision.data.network.model.MoviesResponse
import com.softvision.data.repository.MoviesRepositoryImpl
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.MovieDetails
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExplorerRepositoryTest {
    private lateinit var repository: MoviesRepositoryImpl

    private lateinit var mockResult: MoviesResponse
    private lateinit var mockDetailsList: List<BaseItemDetails>
    private lateinit var mockResponseList: List<MovieResponse>
    private lateinit var mockEntityList: Single<List<MovieEntity>>
    private lateinit var api: ApiEndpoints
    private lateinit var itemsDAO: MoviesDAO

    private lateinit var connectivity: Connectivity

    private lateinit var itemE: MovieEntity
    private lateinit var itemR: MovieResponse
    private lateinit var itemD: MovieDetails

    private val page: Int = 1

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setUp() {
        itemR = mockk()
        itemE = mockk {
            every { id } returns 1
            every { categories } returns mockk()
        }
        itemD = mockk()

        mockResponseList = listOf(itemR, itemR)
        mockEntityList = Single.just(listOf(itemE, itemE))
        mockDetailsList = listOf(itemD, itemD)

        every { itemR.mapToRoomEntity(any()) } returns itemE
        every { itemE.mapToDomainModel() } returns itemD

        mockResult = mockk {
            every { getContent() } returns mockResponseList
        }

        api = mockk(relaxed = true)

        itemsDAO = mockk(relaxUnitFun = true) {
            every { getItem(any()) } returns itemE
            every { loadItemsByCategory(any()) } returns mockEntityList
            // NO NEED TO MOCK METHODS THAT RETURN UNIT BECAUSE ITEMSDAO WAS MOCKED WITH RELAXUNITFUN = TRUE
            // every { itemsDAO.insertItem(itemE) } returns Unit
        }

        connectivity = mockk()

        repository = MoviesRepositoryImpl(itemsDAO, api, connectivity)
    }

    @Test
    fun `repository get trending movies`() {
        val mediaType: String = MovieDataType.TRENDING_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchTrendingMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns true

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchTrendingMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get trending movies with update`() {
        val mediaType: String = MovieDataType.TRENDING_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchTrendingMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns false

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchTrendingMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get popular movies`() {
        val mediaType: String = MovieDataType.POPULAR_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchPopularMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns true

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchPopularMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get popular movies with update`() {
        val mediaType: String = MovieDataType.POPULAR_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchPopularMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns false

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchPopularMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get coming soon movies`() {
        val mediaType: String = MovieDataType.COMING_SOON_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchComingSoonMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns true

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchComingSoonMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get coming soon movies with update`() {
        val mediaType: String = MovieDataType.COMING_SOON_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchComingSoonMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns false

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchComingSoonMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @After
    fun clear() {
        clearAllMocks()
    }
}