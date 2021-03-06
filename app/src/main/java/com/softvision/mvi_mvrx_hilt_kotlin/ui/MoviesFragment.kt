package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.*
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.GenreDetails
import com.softvision.domain.mvi.MoviesByGenreState
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.GenresAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.ItemsAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentMoviesBinding
import com.softvision.mvi_mvrx_hilt_kotlin.utils.setInfiniteScrolling
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment: Fragment(), MavericksView {

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var genresAdapter: GenresAdapter

    private var disposables: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: MoviesViewModel.Factory
    private val moviesViewModel: MoviesViewModel by fragmentViewModel(MoviesViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater)
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
        binding.moviesRecyclerView.apply {
            adapter = itemsAdapter
            layoutManager = gridLayoutManager
            setInfiniteScrolling(layoutManager as GridLayoutManager){
                moviesViewModel.loadMoreMoviesWithSelectedGenre()
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
        binding.genresSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
                val genre = genresAdapter.getItem(position)
                (genre as? GenreDetails)?.let {
                    Timber.i("Movies: genre selected %s", it.name)
                    moviesViewModel.updateSelectedGenre(it)
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
        moviesViewModel.onEach(MoviesByGenreState::displayedGenre) {
            (it as? GenreDetails)?.let { genre ->
                Timber.i("Movies: %s genre selected => display movies", genre.name)
                moviesViewModel.fetchMoviesByGenre()
            }
        }
    }

    private fun initMoviesListener() {
        moviesViewModel.onAsync(
            MoviesByGenreState::moviesByGenreRequest,
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

        moviesViewModel.onEach(MoviesByGenreState::moviesByGenreList){
            if (it.isEmpty()) {
                Timber.i("Movies: movies select subscribe - is empty")
            } else {
                Timber.i("Movies: movies select subscribe - not empty")
            }
            updateMoviesList(it)
        }
    }

    private fun initGenresListener() {
        moviesViewModel.onAsync(
            MoviesByGenreState::genresRequest,
            onSuccess = {
                updateLoader(View.GONE)
                updateNoDataLabel()
            },
            onFail = {
                updateLoader(View.GONE)
                updateNoDataLabel()
            }
        )

        moviesViewModel.onEach(MoviesByGenreState::genres) { list ->
            if (list.isNotEmpty()) {
                updateGenresDropdown()
            }
        }
    }

    private fun setItemSelectionListener() {
        moviesViewModel.onEach(MoviesByGenreState::selectedItem) { item ->
            item?.let {
                displayDetails(item)
            }
        }
    }

    /*
        ------------------ UPDATE UI ------------------
    */

    private fun updateNoDataLabel() {
        withState(moviesViewModel) { state ->
            if (state.moviesByGenreList.isEmpty() || state.genres.isEmpty()) {
                showNoDataMessage()
            } else {
                hideNoDataMessage()
            }
        }
    }

    private fun showNoDataMessage() {
        binding.noMoviesImgView.visibility = View.VISIBLE
    }

    private fun hideNoDataMessage() {
        binding.noMoviesImgView.visibility = View.GONE
    }

    private fun updateMoviesList(list: List<BaseItemDetails>) {
        itemsAdapter.updateData(list)
    }

    private fun updateLoader(visibility: Int, message: String = "") {
        binding.loadingMessage.text = message
        binding.moviesProgressBar.visibility = visibility
        binding.loadingMessage.visibility = visibility
    }

    private fun updateGenresDropdown() {
        Timber.i("Movies: spinner redraw")
        withState(moviesViewModel) { state ->
            genresAdapter.addData(state.genres)

            (state.displayedGenre as? GenreDetails)?.let {
                binding.genresSpinner.setSelection(genresAdapter.getPosition(it), true)
            }
        }
    }

    override fun invalidate() {
        withState(moviesViewModel) { state ->
            if (state.genresRequest is Loading) {
                hideNoDataMessage()
                updateLoader(View.VISIBLE, getString(R.string.loading_genres))
            }

            if (state.moviesByGenreRequest is Loading) {
                hideNoDataMessage()
                updateLoader(View.VISIBLE, getString(R.string.loading_movies))
            }
        }
    }

    /*
        ------------------ SELECT ITEM HANDLERS ------------------
     */

    private fun setSelectedItem(item: BaseItemDetails?) {
        moviesViewModel.setSelectedItem(item)
    }

    private fun displayDetails(item: BaseItemDetails) {
        moviesViewModel.setSelectedItem(null)
        showDetails(item)
    }

    private fun showDetails(item: BaseItemDetails) {
        findNavController().navigate(MoviesFragmentDirections.actionNavigationMoviesToDetailsFragment(item))
    }
}
