package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class ExplorerFragment : Fragment(), MvRxView {

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

    private var disposables: CompositeDisposable = CompositeDisposable()

    companion object {
        private const val TAG = "ExplorerFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentExplorerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        initListeners()
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
        // Item selection listener without Rx
        // comingSoonMoviesAdapter = MoviesAdapter { item: TMDBMovieDetails ->
        //    setSelectedItem(item)
        // }

        // Item selection listener using Rx
        comingSoonMoviesAdapter = MoviesAdapter()
        val comingSoonMoviesDisposable = comingSoonMoviesAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(comingSoonMoviesDisposable)

        binding.comingSoonMoviesLayout.comingSoonRecyclerView.apply {
            adapter = comingSoonMoviesAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager) {
                explorerViewModel.loadMoreComingSoonMovies()
            }
        }
    }

    private fun setPopularMoviesLayout() {
        // Item selection listener without Rx
        // popularMoviesAdapter = MoviesAdapter { item: TMDBMovieDetails ->
        //    setSelectedItem(item)
        // }

        // Item selection listener using Rx
        popularMoviesAdapter = MoviesAdapter()
        val popularMoviesDisposable = popularMoviesAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(popularMoviesDisposable)

        binding.popularMoviesLayout.popularRecyclerView.apply {
            adapter = popularMoviesAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager) {
                explorerViewModel.loadMorePopularMovies()
            }
        }
    }

    private fun setTrendingMoviesLayout() {
        // Item selection listener without Rx
        // trendingMoviesAdapter = MoviesAdapter { item: TMDBMovieDetails ->
        //    setSelectedItem(item)
        // }

        // Item selection listener using Rx
        trendingMoviesAdapter = MoviesAdapter()
        val trendingMoviesDisposable = trendingMoviesAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(trendingMoviesDisposable)

        binding.trendingMoviesLayout.trendingMoviesRecyclerView.apply {
            adapter = trendingMoviesAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager) {
                explorerViewModel.loadMoreTrendingMovies()
            }
        }
    }

    private fun setComingSoonTVShowsLayout() {
        // Item selection listener without Rx
//        comingSoonTVShowsAdapter = TVShowsAdapter { item: TMDBTVShowDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        comingSoonTVShowsAdapter = TVShowsAdapter()
        val comingSoonTvShowsDisposable = comingSoonTVShowsAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(comingSoonTvShowsDisposable)

        binding.comingSoonTVShowsLayout.comingSoonRecyclerView.apply {
            adapter = comingSoonTVShowsAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager) {
                explorerViewModel.loadMoreComingSoonTVShows()
            }
        }
    }

    private fun setPopularTVShowsLayout() {
        // Item selection listener without Rx
//        popularTVShowsAdapter = TVShowsAdapter { item: TMDBTVShowDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        popularTVShowsAdapter = TVShowsAdapter()
        val popularTvShowsDisposable = popularTVShowsAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(popularTvShowsDisposable)


        binding.popularTVShowsLayout.popularRecyclerView.apply {
            adapter = popularTVShowsAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager) {
                explorerViewModel.loadMorePopularTVShows()
            }
        }
    }

    private fun setTrendingTVShowsLayout() {
        // Item selection listener without Rx
//        trendingTVShowsAdapter = TVShowsAdapter { item: TMDBTVShowDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        trendingTVShowsAdapter = TVShowsAdapter()
        val trendingTvShowsDisposable = trendingTVShowsAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(trendingTvShowsDisposable)

        binding.trendingTVShowsLayout.trendingTVShowsRecyclerView.apply {
            adapter = trendingTVShowsAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager) {
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
                updateNoComingSoonMoviesLabel(View.VISIBLE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::comingSoonMovies) { list ->

            updateComingSoonMoviesList(list)
        }
    }

    private fun setComingSoonTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::comingSoonTVShowsRequest,
            onFail = {
                updateComingSoonTVShowsLoader(View.GONE)
                updateNoComingSoonTVShowsLabel(View.VISIBLE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::comingSoonTVShows) { list ->
            updateComingSoonTVShowsList(list)
        }
    }

    private fun setPopularMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::popularMoviesRequest,
            onFail = {
                updatePopularMoviesLoader(View.GONE)
                updateNoPopularMoviesLabel(View.VISIBLE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::popularMovies) { list ->
            updatePopularMoviesList(list)
        }
    }

    private fun setPopularTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::popularTVShowsRequest,
            onFail = {
                updatePopularTVShowsLoader(View.GONE)
                updateNoPopularTVShowsLabel(View.VISIBLE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::popularTVShows) { list ->
            updatePopularTVShowsList(list)
        }
    }

    private fun setTrendingMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::trendingMoviesRequest,
            onFail = {
                updateTrendingMoviesLoader(View.GONE)
                updateNoTrendingMoviesLabel(View.VISIBLE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::trendingMovies) { list ->
            updateTrendingMoviesList(list)
        }
    }

    private fun setTrendingTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::trendingTVShowsRequest,
            onFail = {
                updateTrendingTVShowsLoader(View.GONE)
                updateNoTrendingTVShowsLabel(View.VISIBLE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::trendingTVShows) { list ->
            updateTrendingTVShowsList(list)
        }
    }

    /*
        ------------------ UPDATE UI ------------------
    */

    private fun updateNoTrendingMoviesLabel(visibility: Int) {
        binding.trendingMoviesLayout.noTrendingMoviesImgView.visibility = visibility
    }

    private fun updateNoTrendingTVShowsLabel(visibility: Int) {
        binding.trendingTVShowsLayout.noTrendingTVShowsImgView.visibility = visibility
    }

    private fun updateNoPopularMoviesLabel(visibility: Int) {
        binding.popularMoviesLayout.noPopularMoviesImgView.visibility = visibility
    }

    private fun updateNoPopularTVShowsLabel(visibility: Int) {
        binding.popularTVShowsLayout.noPopularTVShowsImgView.visibility = visibility
    }

    private fun updateNoComingSoonMoviesLabel(visibility: Int) {
        binding.comingSoonMoviesLayout.noComingSoonMoviesImgView.visibility = visibility
    }

    private fun updateNoComingSoonTVShowsLabel(visibility: Int) {
        binding.comingSoonTVShowsLayout.noComingSoonTVShowsImgView.visibility = visibility
    }

    private fun updateTrendingMoviesLoader(visibility: Int) {
        binding.trendingMoviesLayout.apply {
            trendingMoviesProgressBar.visibility = visibility
        }
    }

    private fun updateTrendingTVShowsLoader(visibility: Int) {
        binding.trendingTVShowsLayout.apply {
            trendingTVShowsProgressBar.visibility = visibility
        }
    }

    private fun updatePopularMoviesLoader(visibility: Int) {
        binding.popularMoviesLayout.apply {
            popularMoviesProgressBar.visibility = visibility
        }
    }

    private fun updatePopularTVShowsLoader(visibility: Int) {
        binding.popularTVShowsLayout.apply {
            popularTVShowsProgressBar.visibility = visibility
        }
    }

    private fun updateComingSoonMoviesLoader(visibility: Int) {
        binding.comingSoonMoviesLayout.apply {
            comingSoonMoviesProgressBar.visibility = visibility
        }
    }

    private fun updateComingSoonTVShowsLoader(visibility: Int) {
        binding.comingSoonTVShowsLayout.apply {
            comingSoonTVShowsProgressBar.visibility = visibility
        }
    }

    private fun updateTrendingMoviesList(items: List<TMDBMovieDetails>) {
        updateTrendingMoviesLoader(View.GONE)
        if (items.isNotEmpty()) {
            trendingMoviesAdapter.addData(items)
            binding.trendingMoviesLayout.noTrendingMoviesImgView.visibility = View.GONE
        }
    }

    private fun updateTrendingTVShowsList(items: List<TMDBTVShowDetails>) {
        updateTrendingTVShowsLoader(View.GONE)
        if (items.isNotEmpty()) {
            trendingTVShowsAdapter.addData(items)
            binding.trendingTVShowsLayout.noTrendingTVShowsImgView.visibility = View.GONE
        }
    }

    private fun updatePopularMoviesList(items: List<TMDBMovieDetails>) {
        updatePopularMoviesLoader(View.GONE)
        if (items.isNotEmpty()) {
            binding.popularMoviesLayout.noPopularMoviesImgView.visibility = View.GONE
            popularMoviesAdapter.addData(items)
        }
    }

    private fun updatePopularTVShowsList(items: List<TMDBTVShowDetails>) {
        updatePopularTVShowsLoader(View.GONE)
        if (items.isNotEmpty()) {
            binding.popularTVShowsLayout.noPopularTVShowsImgView.visibility = View.GONE
            popularTVShowsAdapter.addData(items)
        }
    }

    private fun updateComingSoonMoviesList(items: List<TMDBMovieDetails>) {
        updateComingSoonMoviesLoader(View.GONE)
        if (items.isNotEmpty()) {
            binding.comingSoonMoviesLayout.noComingSoonMoviesImgView.visibility = View.GONE
            comingSoonMoviesAdapter.addData(items)
        }
    }

    private fun updateComingSoonTVShowsList(items: List<TMDBTVShowDetails>) {
        updateComingSoonTVShowsLoader(View.GONE)
        if (items.isNotEmpty()) {
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
        showDetails(item)
    }

//    private fun showError() {
//        Toast.makeText(requireContext(), "Failed to load watchlist", Toast.LENGTH_SHORT).show()
//    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun showDetails(item: TMDBItemDetails) {
        findNavController().navigate(
            ExplorerFragmentDirections.actionNavigationExplorerToDetailsFragment(
                item
            )
        )
    }
}