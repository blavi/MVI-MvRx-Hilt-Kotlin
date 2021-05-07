package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.MovieDetails
import com.softvision.domain.model.TVShowDetails
import com.softvision.mvi_mvrx_hilt_kotlin.BuildConfig
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var item: BaseItemDetails

    companion object {
        const val ITEM = "item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ITEM)) {
                item = it.getParcelable<BaseItemDetails>(ITEM)!!
            }
        }

        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offsetFromTop = 150
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            expandedOffset = offsetFromTop
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        (item as? MovieDetails)?.apply {
            displayMovieDetails(this)
        }

        (item as? TVShowDetails)?.apply {
            displayTVShowDetails(this)
        }
    }

    private fun displayTVShowDetails(item: TVShowDetails) {
        item.apply {
            setPoster(backdropPath)
            setTitle(title)
            setDescription(overview)
        }
    }

    private fun displayMovieDetails(item: MovieDetails) {
        item.apply {
            setPoster(backdropPath)
            setTitle(title)
            setDescription(overview)
        }
    }

    private fun setDescription(description: String) {
        binding.description.text = description
    }

    private fun setTitle(title: String) {
        binding.title.text = title
    }

    private fun setPoster(posterPath: String?) {
        posterPath?.let {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.ic_baseline_hourglass_bottom_24)
            requestOptions.error(R.drawable.ic_baseline_mood_bad_24)
            Timber.i("Explore State: item selected - display details")
            Glide.with(binding.poster.context)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(BuildConfig.IMAGE_BASE_URL + it))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(binding.poster)
        }
    }
}