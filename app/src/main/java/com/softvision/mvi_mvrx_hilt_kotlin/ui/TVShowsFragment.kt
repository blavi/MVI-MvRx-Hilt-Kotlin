package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.GenreDetails
import com.softvision.domain.mvi.TVShowsByGenreState
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.GenresAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.ItemsAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentTvShowsBinding
import com.softvision.mvi_mvrx_hilt_kotlin.utils.setInfiniteScrolling
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.TVShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TVShowsFragment : Fragment(), MvRxView {

    private lateinit var binding: FragmentTvShowsBinding

    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var genresAdapter: GenresAdapter

    private var disposables: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: TVShowsViewModel.Factory
    private val tvShowsViewModel: TVShowsViewModel by fragmentViewModel(TVShowsViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowsBinding.inflate(inflater)
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
        setGenresLayout()
        setMoviesByGenreLayout()
    }

    private fun initListeners() {
        initGenreSelectionListener()
        initGenresListener()
        initMoviesListener()
        setItemSelectionListener()
    }

    /*
        ------------------ UI SETUP ------------------
     */

    private fun setMoviesByGenreLayout() {
//        moviesAdapter = MoviesAdapter { item: TMDBItemDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        itemsAdapter = ItemsAdapter()
        val popularTvShowsDisposable = itemsAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(popularTvShowsDisposable)

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.tvShowsRecyclerView.apply {
            adapter = itemsAdapter
            layoutManager = gridLayoutManager
            setInfiniteScrolling(layoutManager as GridLayoutManager){
                tvShowsViewModel.loadMoreMoviesWithSelectedGenre()
            }
        }
    }

    private fun setGenresLayout() {
        genresAdapter = GenresAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf()
        )
        binding.genresSpinner.adapter = genresAdapter
        binding.genresSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
                val genre = genresAdapter.getItem(position)
                (genre as? GenreDetails)?.let {
                    Timber.i("Movies: genre selected %s", it.name)
                    tvShowsViewModel.updateSelectedGenre(it)
                }
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {
                // do nothing
            }
        }
    }

    /*
        ------------------ LISTENERS ------------------
     */
    private fun initGenreSelectionListener() {
        tvShowsViewModel.selectSubscribe(TVShowsByGenreState::displayedGenre) {
            (it as? GenreDetails)?.let { genre ->
                Timber.i("Movies: %s genre selected => display movies", genre.name)
                tvShowsViewModel.fetchTvShowsByGenre()
            }
        }
    }

    private fun initMoviesListener() {
        tvShowsViewModel.asyncSubscribe(
            TVShowsByGenreState::tvShowsByGenreRequest,
            onSuccess = {
                Timber.i("Movies: movies async subscribe - success")
                updateLoader(View.GONE)
                updateNoDataLabel()
            },
            onFail = {
                Timber.i("Movies: movies async subscribe - failed %s - ", it.localizedMessage)
                updateLoader(View.GONE)
                updateNoDataLabel()
            }
        )

        tvShowsViewModel.selectSubscribe(TVShowsByGenreState::tvShowsByGenreList){
            if (it.isEmpty()) {
                Timber.i("Movies: movies select subscribe - is empty")
            } else {
                Timber.i("Movies: movies select subscribe - not empty")
            }
            updateMoviesList(it)
        }
    }

    private fun initGenresListener() {
        tvShowsViewModel.asyncSubscribe(
            TVShowsByGenreState::genresRequest,
            onSuccess = {
                updateLoader(View.GONE)
                updateNoDataLabel()
            },
            onFail = {
                updateLoader(View.GONE)
                updateNoDataLabel()
            }
        )

        tvShowsViewModel.selectSubscribe(TVShowsByGenreState::genres) { list ->
            if (list.isNotEmpty()) {
                updateGenresDropdown()
            }
        }
    }

    private fun setItemSelectionListener() {
        tvShowsViewModel.selectSubscribe(TVShowsByGenreState::selectedItem) { item ->
            item?.let {
                displayDetails(item)
            }
        }
    }

    /*
        ------------------ UPDATE UI ------------------
    */

    private fun updateNoDataLabel() {
        withState(tvShowsViewModel) { state ->
            if (state.tvShowsByGenreList.isEmpty() || state.genres.isEmpty()) {
                showNoDataMessage()
            } else {
                hideNoDataMessage()
            }
        }
    }

    private fun showNoDataMessage() {
        binding.noTvShowsImgView.visibility = View.VISIBLE
    }

    private fun hideNoDataMessage() {
        binding.noTvShowsImgView.visibility = View.GONE
    }

    private fun updateMoviesList(list: List<BaseItemDetails>) {
        itemsAdapter.updateData(list)
    }

    private fun updateLoader(visibility: Int, message: String = "") {
        binding.loadingMessage.text = message
        binding.tvShowsProgressBar.visibility = visibility
        binding.loadingMessage.visibility = visibility
    }

    private fun updateGenresDropdown() {
        Timber.i("Movies: spinner redraw")
        withState(tvShowsViewModel) { state ->
            genresAdapter.addData(state.genres)

            (state.displayedGenre as? GenreDetails)?.let {
                binding.genresSpinner.setSelection(genresAdapter.getPosition(it), true)
            }
        }
    }

    override fun invalidate() {
        withState(tvShowsViewModel) { state ->
            if (state.genresRequest is Loading) {
                hideNoDataMessage()
                updateLoader(View.VISIBLE, getString(R.string.loading_genres))
            }

            if (state.tvShowsByGenreRequest is Loading) {
                hideNoDataMessage()
                updateLoader(View.VISIBLE, getString(R.string.loading_movies))
            }
        }
    }

    /*
        ------------------ SELECT ITEM HANDLERS ------------------
     */

    private fun setSelectedItem(item: BaseItemDetails?) {
        tvShowsViewModel.setSelectedItem(item)
    }

    private fun displayDetails(item: BaseItemDetails) {
        tvShowsViewModel.setSelectedItem(null)
        showDetails(item)
    }

    private fun showDetails(item: BaseItemDetails) {
        findNavController().navigate(TVShowsFragmentDirections.actionNavigationTvShowsToDetailsFragment(item))
    }
}