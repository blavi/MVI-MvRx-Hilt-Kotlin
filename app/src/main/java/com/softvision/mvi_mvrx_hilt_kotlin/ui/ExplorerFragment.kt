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
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.mvi.ExplorerState
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.ItemsAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentExplorerBinding
import com.softvision.mvi_mvrx_hilt_kotlin.utils.setInfiniteScrolling
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.ExplorerViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ExplorerFragment : Fragment(), MvRxView {

    private lateinit var binding: FragmentExplorerBinding

    @Inject
    lateinit var viewModelFactory: ExplorerViewModel.Factory
    private val explorerViewModel: ExplorerViewModel by fragmentViewModel(ExplorerViewModel::class)

    private lateinit var trendingMoviesAdapter: ItemsAdapter
    private lateinit var popularMoviesAdapter: ItemsAdapter
    private lateinit var comingSoonMoviesAdapter: ItemsAdapter

    private lateinit var trendingTVShowsAdapter: ItemsAdapter
    private lateinit var popularTVShowsAdapter: ItemsAdapter
    private lateinit var comingSoonTVShowsAdapter: ItemsAdapter

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

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
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
        // comingSoonMoviesAdapter = MoviesAdapter { item: TMDBItemDetails ->
        //    setSelectedItem(item)
        // }

        // Item selection listener using Rx
        comingSoonMoviesAdapter = ItemsAdapter()
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
        // popularMoviesAdapter = MoviesAdapter { item: TMDBItemDetails ->
        //    setSelectedItem(item)
        // }

        // Item selection listener using Rx
        popularMoviesAdapter = ItemsAdapter()
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
        // trendingMoviesAdapter = MoviesAdapter { item: TMDBItemDetails ->
        //    setSelectedItem(item)
        // }

        // Item selection listener using Rx
        trendingMoviesAdapter = ItemsAdapter()
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
//        comingSoonItemsAdapter = ItemsAdapter { item: TMDBTVShowDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        comingSoonTVShowsAdapter = ItemsAdapter()
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
//        popularItemsAdapter = ItemsAdapter { item: TMDBTVShowDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        popularTVShowsAdapter = ItemsAdapter()
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
//        trendingItemsAdapter = ItemsAdapter { item: TMDBTVShowDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        trendingTVShowsAdapter = ItemsAdapter()
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
                Timber.i("explorer - trending movies loading")
                updateTrendingMoviesLoader(View.VISIBLE)
            }

            if (state.trendingTVShowsRequest is Loading) {
                Timber.i("explorer - trending tv shows loading")
                updateTrendingTVShowsLoader(View.VISIBLE)
            }

            if (state.popularMoviesRequest is Loading) {
                Timber.i("explorer - popular movies loading")
                updatePopularMoviesLoader(View.VISIBLE)
            }

            if (state.popularTVShowsRequest is Loading) {
                Timber.i("explorer - popular tv shows loading")
                updatePopularTVShowsLoader(View.VISIBLE)
            }

            if (state.comingSoonMoviesRequest is Loading) {
                Timber.i("explorer - coming soon movies loading")
                updateComingSoonMoviesLoader(View.VISIBLE)
            }

            if (state.comingSoonTVShowsRequest is Loading) {
                Timber.i("explorer - coming soon tv shows loading")
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
            onSuccess = {
                updateComingSoonMoviesLoader(View.GONE)
                updateNoComingSoonMoviesLabel()
            },
            onFail = {
                updateComingSoonMoviesLoader(View.GONE)
                updateNoComingSoonMoviesLabel()
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::comingSoonMovies) { list ->
            updateComingSoonMoviesLoader(View.GONE)
            updateComingSoonMoviesList(list)
        }
    }

    private fun setComingSoonTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::comingSoonTVShowsRequest,
            onSuccess = {
                updateComingSoonTVShowsLoader(View.GONE)
                updateNoComingSoonTVShowsLabel()
            },
            onFail = {
                updateComingSoonTVShowsLoader(View.GONE)
                updateNoComingSoonTVShowsLabel()
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::comingSoonTVShows) { list ->
            updateComingSoonTVShowsLoader(View.GONE)
            updateComingSoonTVShowsList(list)
        }
    }

    private fun setPopularMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::popularMoviesRequest,
            onSuccess = {
                updatePopularMoviesLoader(View.GONE)
                updateNoPopularMoviesLabel()
            },
            onFail = {
                updatePopularMoviesLoader(View.GONE)
                updateNoPopularMoviesLabel()
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::popularMovies) { list ->
            updatePopularMoviesLoader(View.GONE)
            updatePopularMoviesList(list)
        }
    }

    private fun setPopularTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::popularTVShowsRequest,
            onSuccess = {
                updatePopularTVShowsLoader(View.GONE)
                updateNoPopularTVShowsLabel()
            },
            onFail = {
                updatePopularTVShowsLoader(View.GONE)
                updateNoPopularTVShowsLabel()
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::popularTVShows) { list ->
            updatePopularTVShowsLoader(View.GONE)
            updatePopularTVShowsList(list)
        }
    }

    private fun setTrendingMoviesListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::trendingMoviesRequest,
            onSuccess = {
                updateTrendingMoviesLoader(View.GONE)
                updateNoTrendingMoviesLabel()
            },
            onFail = {
                updateTrendingMoviesLoader(View.GONE)
                updateNoTrendingMoviesLabel()
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::trendingMovies) { list ->
            updateTrendingMoviesLoader(View.GONE)
            updateTrendingMoviesList(list)
        }
    }

    private fun setTrendingTVShowsListeners() {
        explorerViewModel.asyncSubscribe(ExplorerState::trendingTVShowsRequest,
            onSuccess = {
                updateTrendingTVShowsLoader(View.GONE)
                updateNoTrendingTVShowsLabel()
            },
            onFail = {
                updateTrendingTVShowsLoader(View.GONE)
                updateNoTrendingTVShowsLabel()
            }
        )

        explorerViewModel.selectSubscribe(ExplorerState::trendingTVShows) { list ->
            updateTrendingTVShowsLoader(View.GONE)
            updateTrendingTVShowsList(list)
        }
    }

    /*
        ------------------ UPDATE UI ------------------
    */

    private fun updateNoTrendingMoviesLabel() {
        withState(explorerViewModel) { state ->
            binding.trendingMoviesLayout.noTrendingMoviesImgView.visibility = if (state.trendingMovies.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun updateNoTrendingTVShowsLabel() {
        withState(explorerViewModel) { state ->
            binding.trendingTVShowsLayout.noTrendingTVShowsImgView.visibility = if (state.trendingTVShows.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun updateNoPopularTVShowsLabel() {
        withState(explorerViewModel) { state ->
            binding.popularTVShowsLayout.noPopularTVShowsImgView.visibility = if (state.popularTVShows.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun updateNoPopularMoviesLabel() {
        withState(explorerViewModel) { state ->
            binding.popularMoviesLayout.noPopularMoviesImgView.visibility = if (state.popularMovies.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun updateNoComingSoonMoviesLabel() {
        withState(explorerViewModel) { state ->
            binding.comingSoonMoviesLayout.noComingSoonMoviesImgView.visibility = if (state.comingSoonMovies.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun updateNoComingSoonTVShowsLabel() {
        withState(explorerViewModel) { state ->
            binding.comingSoonTVShowsLayout.noComingSoonTVShowsImgView.visibility = if (state.comingSoonTVShows.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
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

    private fun updateTrendingMoviesList(items: List<BaseItemDetails>) {
        trendingMoviesAdapter.updateData(items)
    }

    private fun updateTrendingTVShowsList(items: List<BaseItemDetails>) {
        trendingTVShowsAdapter.updateData(items)
    }

    private fun updatePopularMoviesList(items: List<BaseItemDetails>) {
        popularMoviesAdapter.updateData(items)
    }

    private fun updatePopularTVShowsList(items: List<BaseItemDetails>) {
        popularTVShowsAdapter.updateData(items)
    }

    private fun updateComingSoonMoviesList(items: List<BaseItemDetails>) {
        comingSoonMoviesAdapter.updateData(items)
    }

    private fun updateComingSoonTVShowsList(items: List<BaseItemDetails>) {
        comingSoonTVShowsAdapter.updateData(items)
    }

    /*
        ------------------ SELECT ITEM HANDLERS ------------------
     */

    private fun setSelectedItem(item: BaseItemDetails?) {
        explorerViewModel.setSelectedItem(item)
    }

    private fun displayDetails(item: BaseItemDetails) {
        setSelectedItem(null)
        showDetails(item)
    }

    private fun showDetails(item: BaseItemDetails) {
        findNavController().navigate(
            ExplorerFragmentDirections.actionNavigationExplorerToDetailsFragment(
                item
            )
        )
    }
}