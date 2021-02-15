package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.domain.mvi.ExplorerState
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.MoviesAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.TVShowsAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentExplorerBinding
import com.softvision.mvi_mvrx_hilt_kotlin.utils.setInfiniteScrolling
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.ExplorerViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ExplorerFragment: Fragment(), MvRxView {

    private lateinit var binding: FragmentExplorerBinding

    @Inject
    lateinit var viewModelFactory: ExplorerViewModel.Factory
    private val explorerViewModel: ExplorerViewModel by fragmentViewModel(ExplorerViewModel::class)

    private lateinit var trendingMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var comingSoonMoviesAdapter: MoviesAdapter

    private lateinit var trendingTVShowsAdapter: TVShowsAdapter
    private lateinit var popularTVShowsAdapter: TVShowsAdapter
    private lateinit var comingSoonTVShowsAdapter: TVShowsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentExplorerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        initListeners()
        explorerViewModel.fetchTMDBItems()
    }

    private fun setupUI() {
        setTrendingMoviesLayout()
        setPopularMoviesLayout()
        setComingSoonMoviesLayout()

        setTrendingTVShowsLayout()
        setPopularTVShowsLayout()
        setComingSoonTVShowsLayout()
    }

    private fun initListeners() {
        setTrendingMoviesListeners()
        setTrendingTVShowsListeners()

        setPopularMoviesListeners()
        setPopularTVShowsListeners()

        setComingSoonMoviesListeners()
        setComingSoonTVShowsListeners()

        setItemSelectionListener()
    }

    /*
        ------------------ UI SETUP ------------------
     */

    private fun setComingSoonMoviesLayout() {
        comingSoonMoviesAdapter = MoviesAdapter { item: TMDBMovieDetails ->
            setSelectedItem(item)
        }
        binding.comingSoonMoviesLayout.comingSoonRecyclerView.apply {
            adapter = comingSoonMoviesAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMoreComingSoonMovies()
            }
        }
    }

    private fun setPopularMoviesLayout() {
        popularMoviesAdapter = MoviesAdapter { item: TMDBMovieDetails ->
            setSelectedItem(item)
        }
        binding.popularMoviesLayout.popularRecyclerView.apply {
            adapter = popularMoviesAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMorePopularMovies()
            }
        }
    }

    private fun setTrendingMoviesLayout() {
        trendingMoviesAdapter = MoviesAdapter { item: TMDBMovieDetails ->
            setSelectedItem(item)
        }
        binding.trendingMoviesLayout.trendingMoviesRecyclerView.apply {
            adapter = trendingMoviesAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMoreTrendingMovies()
            }
        }
    }

    private fun setComingSoonTVShowsLayout() {
        comingSoonTVShowsAdapter = TVShowsAdapter { item: TMDBTVShowDetails ->
            setSelectedItem(item)
        }
        binding.comingSoonTVShowsLayout.comingSoonRecyclerView.apply {
            adapter = comingSoonTVShowsAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMoreComingSoonTVShows()
            }
        }
    }

    private fun setPopularTVShowsLayout() {
        popularTVShowsAdapter = TVShowsAdapter { item: TMDBTVShowDetails ->
            setSelectedItem(item)
        }
        binding.popularTVShowsLayout.popularRecyclerView.apply {
            adapter = popularTVShowsAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMoreTVShowsMovies()
            }
        }
    }

    private fun setTrendingTVShowsLayout() {
        trendingTVShowsAdapter = TVShowsAdapter { item: TMDBTVShowDetails ->
            setSelectedItem(item)
        }
        binding.trendingTVShowsLayout.trendingTVShowsRecyclerView.apply {
            adapter = trendingTVShowsAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMoreTrendingTVShows()
            }
        }
    }

    /*
        ------------------ LISTENERS ------------------
     */
    override fun invalidate() {
        withState(explorerViewModel) { state ->
            if (state.trendingMoviesRequest is Loading) {
                updateTrendingMoviesLoader(View.VISIBLE)
            }

            if (state.trendingTVShowsRequest is Loading) {
                updateTrendingTVShowsLoader(View.VISIBLE)
            }

            if (state.popularMoviesRequest is Loading) {
                updatePopularMoviesLoader(View.VISIBLE)
            }

            if (state.popularTVShowsRequest is Loading) {
                updatePopularTVShowsLoader(View.VISIBLE)
            }

            if (state.comingSoonMoviesRequest is Loading) {
                updateComingSoonMoviesLoader(View.VISIBLE)
            }

            if (state.comingSoonTVShowsRequest is Loading) {
                updateComingSoonTVShowsLoader(View.VISIBLE)
            }
        }
    }

    private fun setItemSelectionListener() {
        explorerViewModel.selectSubscribe(ExplorerState::selectedItem) { item ->
            item?.let {
                displayDetails(item)
            }
        }
    }

    private fun setComingSoonMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::comingSoonMoviesRequest,
            onFail = {
                updateComingSoonMoviesLoader(View.GONE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::comingSoonMovies) { list ->
            Timber.i("Explore State: coming soon display select")
            list.forEach { item ->
                Timber.i("Explore State: %s", item.title)
            }
            updateComingSoonMoviesList(list)
        }
    }

    private fun setComingSoonTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::comingSoonTVShowsRequest,
            onFail = {
                updateComingSoonTVShowsLoader(View.GONE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::comingSoonTVShows) { list ->
            Timber.i("Explore State: coming soon display select")
            list.forEach { item ->
                Timber.i("Explore State: %s", item.title)
            }
            updateComingSoonTVShowsList(list)
        }
    }

    private fun setPopularMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::popularMoviesRequest,
            onFail = {
                updatePopularMoviesLoader(View.GONE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::popularMovies) { list ->
            Timber.i("Explore State: popular display select")
            list.forEach { item ->
                Timber.i("Explore State: %s", item.title)
            }
            updatePopularMoviesList(list)
        }
    }

    private fun setPopularTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::popularTVShowsRequest,
            onFail = {
                updatePopularTVShowsLoader(View.GONE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::popularTVShows) { list ->
            Timber.i("Explore State: popular display select")
            list.forEach { item ->
                Timber.i("Explore State: %s", item.title)
            }
            updatePopularTVShowsList(list)
        }
    }

    private fun setTrendingMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::trendingMoviesRequest,
            onFail = {
                updateTrendingMoviesLoader(View.GONE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::trendingMovies) { list ->
            Timber.i("Explore State: trending display select")
            list.forEach { item ->
                Timber.i("Explore State: TRENDING MOVIE - %s", item.title)
            }
            updateTrendingMoviesList(list)
        }
    }

    private fun setTrendingTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::trendingTVShowsRequest,
            onFail = {
                updateTrendingTVShowsLoader(View.GONE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::trendingTVShows) { list ->
            Timber.i("Explore State: trending display select")
            list.forEach { item ->
                Timber.i("Explore State: TRENDING TV SHOW - %s", item.title)
            }
            updateTrendingTVShowsList(list)
        }
    }

    /*
        ------------------ UPDATE UI ------------------
    */

    private fun updateTrendingMoviesLoader(visibility: Int) {
        Timber.i("Explore State: trending loading")
        binding.trendingMoviesLayout.apply {
            trendingMoviesProgressBar.visibility = visibility
        }
    }

    private fun updateTrendingTVShowsLoader(visibility: Int) {
        Timber.i("Explore State: trending loading")
        binding.trendingTVShowsLayout.apply {
            trendingTVShowsProgressBar.visibility = visibility
        }
    }

    private fun updatePopularMoviesLoader(visibility: Int) {
        Timber.i("Explore State: popular loading")
        binding.popularMoviesLayout.apply {
            popularMoviesProgressBar.visibility = visibility
        }
    }

    private fun updatePopularTVShowsLoader(visibility: Int) {
        Timber.i("Explore State: popular loading")
        binding.popularTVShowsLayout.apply {
            popularTVShowsProgressBar.visibility = visibility
        }
    }

    private fun updateComingSoonMoviesLoader(visibility: Int) {
        Timber.i("Explore State: coming soon loading")
        binding.comingSoonMoviesLayout.apply {
            comingSoonMoviesProgressBar.visibility = visibility
        }
    }

    private fun updateComingSoonTVShowsLoader(visibility: Int) {
        Timber.i("Explore State: coming soon loading")
        binding.comingSoonTVShowsLayout.apply {
            comingSoonTVShowsProgressBar.visibility = visibility
        }
    }

    private fun updateTrendingMoviesList(items: List<TMDBMovieDetails>) {
        updateTrendingMoviesLoader(View.GONE)
        if (items.isEmpty()) {
            Timber.i("Explore State: trending display select empty")
            binding.trendingMoviesLayout.noTrendingMoviesImgView.visibility = View.VISIBLE
        } else {
            Timber.i("Explore State: trending display select not empty")
            trendingMoviesAdapter.addData(items)
            binding.trendingMoviesLayout.noTrendingMoviesImgView.visibility = View.GONE
        }
    }

    private fun updateTrendingTVShowsList(items: List<TMDBTVShowDetails>) {
        updateTrendingTVShowsLoader(View.GONE)
        if (items.isEmpty()) {
            Timber.i("Explore State: trending display select empty")
            binding.trendingTVShowsLayout.noTrendingTVShowsImgView.visibility = View.VISIBLE
        } else {
            Timber.i("Explore State: trending display select not empty")
            trendingTVShowsAdapter.addData(items)
            binding.trendingTVShowsLayout.noTrendingTVShowsImgView.visibility = View.GONE
        }
    }

    private fun updatePopularMoviesList(items: List<TMDBMovieDetails>) {
        updatePopularMoviesLoader(View.GONE)
        if (items.isEmpty()) {
            binding.popularMoviesLayout.noPopularMoviesImgView.visibility = View.VISIBLE
        } else {
            binding.popularMoviesLayout.noPopularMoviesImgView.visibility = View.GONE
            popularMoviesAdapter.addData(items)
        }
    }

    private fun updatePopularTVShowsList(items: List<TMDBTVShowDetails>) {
        updatePopularTVShowsLoader(View.GONE)
        if (items.isEmpty()) {
            binding.popularTVShowsLayout.noPopularTVShowsImgView.visibility = View.VISIBLE
        } else {
            binding.popularTVShowsLayout.noPopularTVShowsImgView.visibility = View.GONE
            popularTVShowsAdapter.addData(items)
        }
    }

    private fun updateComingSoonMoviesList(items: List<TMDBMovieDetails>) {
        updateComingSoonMoviesLoader(View.GONE)
        if (items.isEmpty()) {
            binding.comingSoonMoviesLayout.noComingSoonMoviesImgView.visibility = View.VISIBLE
        } else {
            binding.comingSoonMoviesLayout.noComingSoonMoviesImgView.visibility = View.GONE
            comingSoonMoviesAdapter.addData(items)
        }
    }

    private fun updateComingSoonTVShowsList(items: List<TMDBTVShowDetails>) {
        updateComingSoonTVShowsLoader(View.GONE)
        if (items.isEmpty()) {
            binding.comingSoonTVShowsLayout.noComingSoonTVShowsImgView.visibility = View.VISIBLE
        } else {
            binding.comingSoonTVShowsLayout.noComingSoonTVShowsImgView.visibility = View.GONE
            comingSoonTVShowsAdapter.addData(items)
        }
    }

    /*
        ------------------ SELECT ITEM HANDLERS ------------------
     */

    private fun setSelectedItem(item: TMDBItemDetails?) {
        explorerViewModel.setSelectedItem(item)
    }

    private fun displayDetails(item: TMDBItemDetails) {
        explorerViewModel.setSelectedItem(null)
        (requireActivity() as MainActivity).goToDetails(item)
    }

//    private fun showError() {
//        Toast.makeText(requireContext(), "Failed to load watchlist", Toast.LENGTH_SHORT).show()
//    }
}