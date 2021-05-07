package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.mvi.SearchState
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.adapter.ItemsAdapter
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentSearchBinding
import com.softvision.mvi_mvrx_hilt_kotlin.utils.RxSearchObservable
import com.softvision.mvi_mvrx_hilt_kotlin.utils.setInfiniteScrolling
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment(), MvRxView {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var itemsAdapter: ItemsAdapter

    private var disposables: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory
    private val searchViewModel: SearchViewModel by fragmentViewModel(SearchViewModel::class)

    companion object {
        private const val TAG = "SearchFragment"
    }

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
        initUpdateListeners()
        initSearchViewListener()
        initItemSelectionListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun setupUI() {
        // Item selection listener using Rx
        itemsAdapter = ItemsAdapter()
        val popularTvShowsDisposable = itemsAdapter.clickEvent
            .subscribe {
                setSelectedItem(it)
            }
        disposables.add(popularTvShowsDisposable)

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.itemsRecyclerView.apply {
            adapter = itemsAdapter
            layoutManager = gridLayoutManager
            setInfiniteScrolling(layoutManager as GridLayoutManager) {
                searchViewModel.loadMoreItems()
            }
        }

        val searchView: View = binding.searchView
        if (searchView != null) {
            val searchText = searchView.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)
            if (searchText != null) {
                searchText.maxLines = 1
                searchText.isSingleLine = true
                searchText.setTextColor(resources.getColor(R.color.magnolia))
                searchText.setHintTextColor(resources.getColor(R.color.battleship_gray))
                searchText.background = null
            }
        }
    }

    /*
        ------------------ LISTENERS ------------------
    */

    private fun initUpdateListeners() {
        searchViewModel.onAsync(
            SearchState::searchRequest,
            onSuccess = {
                Timber.i("%s Query async subscribe success", TAG)
                updateLoader(View.GONE)
                updateNoDataLabel()
            },
            onFail = {
                Timber.i("%s Query async subscribe %s", TAG, it.localizedMessage)
                updateLoader(View.GONE)
                updateNoDataLabel()
            }
        )

        searchViewModel.onEach(SearchState::items) { list ->
            updateQueryResult(list)
            hideNoDataMessage()
        }
    }

    private fun initSearchViewListener() {
        val searchObservable = RxSearchObservable.queryObservableFromView(binding.searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { text ->
                text.isNotEmpty() && text.length >= 3
            }
            .distinctUntilChanged()
            .map { text ->
                text.toString().toLowerCase(Locale.getDefault()).trim()
                Timber.i("%s Query map %s", TAG, text)
                searchViewModel.executeQuery(text.toString())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        val closeObservable = RxSearchObservable.closeObservableFromView(binding.searchView)
            .map {
                searchViewModel.clearResults()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        disposables.addAll(searchObservable, closeObservable)
    }

    private fun initItemSelectionListener() {
        searchViewModel.onEach(SearchState::selectedItem) { item ->
            item?.let {
                displayDetails(item)
            }
        }
    }

    /*
        ------------------ UPDATE UI ------------------
    */

    private fun updateQueryResult(list: List<BaseItemDetails>) {
        itemsAdapter.updateData(list)
    }

    private fun updateNoDataLabel() {
        withState(searchViewModel) { state ->
            if (state.items.isEmpty()) {
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

    override fun invalidate() {
        withState(searchViewModel) { state ->
            if (state.searchRequest is Loading) {
                hideNoDataMessage()
                updateLoader(View.VISIBLE, getString(R.string.loading))
            }
        }
    }

    private fun updateLoader(visibility: Int, message: String = "") {
        binding.itemsProgressBar.visibility = visibility
        binding.loadingMessage.text = message
        binding.loadingMessage.visibility = visibility
    }


    /*
    ------------------ SELECT ITEM HANDLERS ------------------
 */

    private fun setSelectedItem(item: BaseItemDetails?) {
        searchViewModel.setSelectedItem(item)
    }

    private fun displayDetails(item: BaseItemDetails) {
        searchViewModel.setSelectedItem(null)
        showDetails(item)
    }

    private fun showDetails(item: BaseItemDetails) {
        findNavController().navigate(
            SearchFragmentDirections.actionNavigationFrgSearchToDetailsFragment(
                item
            )
        )
    }
}