package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.jakewharton.rxbinding4.appcompat.queryTextChangeEvents
import com.softvision.domain.model.TMDBItemDetails
import com.softvision.domain.mvi.SearchState
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.ItemsAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentSearchBinding
import com.softvision.mvi_mvrx_hilt_kotlin.utils.setInfiniteScrolling
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment: Fragment(), MvRxView {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var itemsAdapter: ItemsAdapter

    private var disposables: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory
    private val searchViewModel: SearchViewModel by fragmentViewModel(SearchViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        initListeners()
    }

    private fun setupUI() {
        // Item selection listener using Rx
        itemsAdapter = ItemsAdapter()
        val popularTvShowsDisposable = itemsAdapter.clickEvent
            .subscribe {
//                setSelectedItem(it)
            }
        disposables.add(popularTvShowsDisposable)

        val gridLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.itemsRecyclerView.apply {
            adapter = itemsAdapter
            layoutManager = gridLayoutManager
            setInfiniteScrolling(layoutManager as GridLayoutManager){
//                searchViewModel.loadMoreItems()
            }
        }
    }

    private fun initListeners() {
        searchViewModel.asyncSubscribe(
            SearchState::searchRequest,
            onFail = {
                updateLoader(View.GONE)
                updateNoDataLabel(View.VISIBLE)
            }
        )

        searchViewModel.selectSubscribe(SearchState::items) { list ->
            if (list.isNotEmpty()) {
                updateQueryResult(list)
            }
        }

        binding.searchView.queryTextChangeEvents()
            .skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter {
                Timber.i("Query %s", it.queryText)
                it.queryText.isNotEmpty()
            }
            .distinctUntilChanged()
            .doOnNext {
                Timber.i("Query %s", it.queryText)
                searchViewModel.executeQuery(it.queryText.toString())
            }
            .subscribeOn(Schedulers.io())
    }

    private fun updateQueryResult(list: List<TMDBItemDetails>) {
        updateLoader(View.GONE)
        if (list.isNotEmpty()) {
            itemsAdapter.addData(list)
            binding.noMoviesImgView.visibility = View.GONE
        }
    }

    private fun updateNoDataLabel(visibility: Int) {
        binding.noMoviesImgView.visibility = visibility
    }

    override fun invalidate() {
        withState(searchViewModel) { state ->
            if (state.searchRequest is Loading) {
                updateLoader(View.VISIBLE, getString(R.string.loading_genres))
            }
        }
    }

    private fun updateLoader(visibility: Int, message: String = "") {

    }
}