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
import com.softvision.domain.mvi.ExplorerState
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.ItemsAdapter
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

    private lateinit var trendingAdapter: ItemsAdapter
    private lateinit var popularAdapter: ItemsAdapter
    private lateinit var comingSoonAdapter: ItemsAdapter

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
    }

    private fun initListeners() {
        setTrendingMoviesListeners()
        setPopularMoviesListeners()
        setComingSoonMoviesListeners()
        setItemSelectionListener()
    }

    /*
        ------------------ UI SETUP ------------------
     */

    private fun setComingSoonMoviesLayout() {
        comingSoonAdapter = ItemsAdapter { item: TMDBItemDetails ->
            setSelectedItem(item)
        }
        binding.comingSoonMoviesLayout.comingSoonRecyclerView.apply {
            adapter = comingSoonAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMoreComingSoonMovies()
            }
        }
    }

    private fun setPopularMoviesLayout() {
        popularAdapter = ItemsAdapter { item: TMDBItemDetails ->
            setSelectedItem(item)
        }
        binding.popularMoviesLayout.popularRecyclerView.apply {
            adapter = popularAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMorePopularMovies()
            }
        }
    }

    private fun setTrendingMoviesLayout() {
        trendingAdapter = ItemsAdapter { item: TMDBItemDetails ->
            setSelectedItem(item)
        }
        binding.trendingMoviesLayout.trendingRecyclerView.apply {
            adapter = trendingAdapter
            setInfiniteScrolling(layoutManager as LinearLayoutManager){
                explorerViewModel.loadMoreTrendingMovies()
            }
        }
    }

    /*
        ------------------ LISTENERS ------------------
     */
    override fun invalidate() {
        withState(explorerViewModel) { state ->
            if (state.trendingRequest is Loading) {
                updateTrendingMoviesLoader(View.VISIBLE)
            }

            if (state.popularRequest is Loading) {
                updatePopularMoviesLoader(View.VISIBLE)
            }

            if (state.comingSoonRequest is Loading) {
                updateComingSoonMoviesLoader(View.VISIBLE)
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
        explorerViewModel.asyncSubscribe(ExplorerState::comingSoonRequest,
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

    private fun setPopularMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::popularRequest,
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

    private fun setTrendingMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::trendingRequest,
            onFail = {
                updateTrendingMoviesLoader(View.GONE)
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::trendingMovies) { list ->
            Timber.i("Explore State: trending display select")
            list.forEach { item ->
                Timber.i("Explore State: %s", item.title)
            }
            updateTrendingMoviesList(list)
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

    private fun updatePopularMoviesLoader(visibility: Int) {
        Timber.i("Explore State: popular loading")
        binding.popularMoviesLayout.apply {
            popularMoviesProgressBar.visibility = visibility
        }
    }

    private fun updateComingSoonMoviesLoader(visibility: Int) {
        Timber.i("Explore State: coming soon loading")
        binding.comingSoonMoviesLayout.apply {
            comingSoonMoviesProgressBar.visibility = visibility
        }
    }

    private fun updateTrendingMoviesList(items: List<TMDBItemDetails>) {
        updateTrendingMoviesLoader(View.GONE)
        if (items.isEmpty()) {
            Timber.i("Explore State: trending display select empty")
            binding.trendingMoviesLayout.noDataImgView.visibility = View.VISIBLE
        } else {
            Timber.i("Explore State: trending display select not empty")
            trendingAdapter.addData(items)
            binding.trendingMoviesLayout.noDataImgView.visibility = View.GONE
        }
    }

    private fun updatePopularMoviesList(items: List<TMDBItemDetails>) {
        updatePopularMoviesLoader(View.GONE)
        if (items.isEmpty()) {
            binding.popularMoviesLayout.noDataImgView.visibility = View.VISIBLE
        } else {
            binding.popularMoviesLayout.noDataImgView.visibility = View.GONE
            popularAdapter.addData(items)
        }
    }

    private fun updateComingSoonMoviesList(items: List<TMDBItemDetails>) {
        updateComingSoonMoviesLoader(View.GONE)
        if (items.isEmpty()) {
            binding.comingSoonMoviesLayout.noDataImgView.visibility = View.VISIBLE
        } else {
            binding.comingSoonMoviesLayout.noDataImgView.visibility = View.GONE
            comingSoonAdapter.addData(items)
        }
    }







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