package com.softvision.data

import com.softvision.data.common.Connectivity
import com.softvision.data.database.dao.TMDBMoviesDAO
import com.softvision.data.database.model.TMDBMovieEntity
import com.softvision.data.network.api.ApiEndpoints
import com.softvision.data.network.base.DataType
import com.softvision.data.network.model.TMDBMovieResponse
import com.softvision.data.network.model.TMDBMoviesResponse
import com.softvision.data.repository.MoviesRepositoryImpl
import com.softvision.domain.model.TMDBMovieDetails
import io.mockk.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExplorerRepositoryTest {
    private lateinit var repository: MoviesRepositoryImpl

    private lateinit var mockResult: TMDBMoviesResponse
    private lateinit var mockDetailsList: List<TMDBMovieDetails>
    private lateinit var mockResponseList: List<TMDBMovieResponse>
    private lateinit var mockEntityList: List<TMDBMovieEntity>
    private lateinit var api: ApiEndpoints
    private lateinit var itemsDAO: TMDBMoviesDAO

    private lateinit var connectivity: Connectivity

    private lateinit var itemE: TMDBMovieEntity
    private lateinit var itemR: TMDBMovieResponse
    private lateinit var itemD: TMDBMovieDetails

    private val page: Int = 1

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setUp() {
        itemR = mockk<TMDBMovieResponse>()
        itemE = mockk<TMDBMovieEntity>() {
            every { id } returns 1
            every { categories } returns mockk()
        }
        itemD = mockk<TMDBMovieDetails>()

        mockResponseList = listOf(itemR, itemR)
        mockEntityList = listOf(itemE, itemE)
        mockDetailsList = listOf(itemD, itemD)

        every { itemR.mapToRoomEntity(any()) } returns itemE
        every { itemE.mapToDomainModel() } returns itemD

        mockResult = mockk<TMDBMoviesResponse>() {
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

        repository = MoviesRepositoryImpl(itemsDAO, api)
        repository.connectivity = connectivity
    }

    @Test
    fun `repository get trending movies`() {
        val mediaType: String = DataType.TRENDING_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchTrendingMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns true

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchTrendingMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get trending movies with update`() {
        val mediaType: String = DataType.TRENDING_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchTrendingMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns false

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchTrendingMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get popular movies`() {
        val mediaType: String = DataType.POPULAR_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchPopularMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns true

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchPopularMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get popular movies with update`() {
        val mediaType: String = DataType.POPULAR_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchPopularMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns false

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchPopularMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get coming soon movies`() {
        val mediaType: String = DataType.COMING_SOON_MOVIES

        every { itemE.categories.contains(any())} returns true

        every { api.fetchComingSoonMovies(page = page) } returns Single.just(mockResult)

        every { connectivity.hasNetworkAccess() } returns true

        val test = repository.getData(mediaType, page).test()

        verify { api.fetchComingSoonMovies(page = page) }

        test.assertValue(mockDetailsList)
    }

    @Test
    fun `repository get coming soon movies with update`() {
        val mediaType: String = DataType.COMING_SOON_MOVIES

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