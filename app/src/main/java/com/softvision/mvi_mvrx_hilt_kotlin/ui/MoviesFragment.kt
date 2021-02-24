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
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.mvi.MoviesByGenreState
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.GenresAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.MoviesAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentMoviesBinding
import com.softvision.mvi_mvrx_hilt_kotlin.utils.setInfiniteScrolling
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment: Fragment(), MvRxView {

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var moviesAdapter: MoviesAdapter
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
//        moviesAdapter = MoviesAdapter { item: TMDBMovieDetails ->
//            setSelectedItem(item)
//        }

        // Item selection listener using Rx
        moviesAdapter = MoviesAdapter()
        val popularTvShowsDisposable = moviesAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(popularTvShowsDisposable)

        val gridLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.moviesRecyclerView.apply {
            adapter = moviesAdapter
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
                Timber.i("Movies: genre selected %s", genre.name)
                moviesViewModel.updateSelectedGenre(genre)
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {}
        }
    }

    /*
        ------------------ LISTENERS ------------------
     */
    private fun initGenreSelectionListener() {
        moviesViewModel.selectSubscribe(MoviesByGenreState::displayedGenre) {
            it?.let {
                Timber.i("Movies: %s genre selected => display movies", it.name)
                moviesViewModel.fetchMoviesByGenre()
            }
        }
    }

    private fun initMoviesListener() {
        moviesViewModel.asyncSubscribe(
            MoviesByGenreState::moviesByGenreRequest,
            onFail = {
                updateLoader(View.GONE)
                updateNoDataLabel(View.VISIBLE)
            }
        )

        moviesViewModel.selectSubscribe(MoviesByGenreState::moviesByGenreList){
//            Timber.i("Movies: movies select subscribe")
            updateMoviesList(it)
        }
    }

    private fun initGenresListener() {
        moviesViewModel.asyncSubscribe(
            MoviesByGenreState::genresRequest,
            onFail = {
                updateLoader(View.GONE)
                updateNoDataLabel(View.VISIBLE)
            }
        )

        moviesViewModel.selectSubscribe(MoviesByGenreState::genres) { list ->
            if (list.isNotEmpty()) {
                updateGenresDropdown()
            }
        }
    }

    private fun setItemSelectionListener() {
        moviesViewModel.selectSubscribe(MoviesByGenreState::selectedItem) { item ->
            item?.let {
                displayDetails(item)
            }
        }
    }

    /*
        ------------------ UPDATE UI ------------------
    */

    private fun updateNoDataLabel(visibility: Int) {
        Timber.i("Movies: movies genre no data label %s", visibility)
        binding.noMoviesImgView.visibility = visibility
    }

    private fun updateMoviesList(list: List<TMDBMovieDetails>) {
        updateLoader(View.GONE)
        if (list.isNotEmpty()) {
            moviesAdapter.addData(list)
            binding.noMoviesImgView.visibility = View.GONE
        }
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
            state.displayedGenre?.let {
                binding.genresSpinner.setSelection(genresAdapter.getPosition(it), true)
            }
        }
    }

    override fun invalidate() {
        withState(moviesViewModel) { state ->
            if (state.genresRequest is Loading) {
                updateLoader(View.VISIBLE, getString(R.string.loading_genres))
            }

            if (state.moviesByGenreRequest is Loading) {
                updateLoader(View.VISIBLE, getString(R.string.loading_movies))
            }
        }
    }

    /*
        ------------------ SELECT ITEM HANDLERS ------------------
     */

    private fun setSelectedItem(item: TMDBItemDetails?) {
        moviesViewModel.setSelectedItem(item)
    }

    private fun displayDetails(item: TMDBItemDetails) {
        moviesViewModel.setSelectedItem(null)
        showDetails(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun showDetails(item: TMDBItemDetails) {
        findNavController().navigate(MoviesFragmentDirections.actionNavigationMoviesToDetailsFragment(item))
    }
}
