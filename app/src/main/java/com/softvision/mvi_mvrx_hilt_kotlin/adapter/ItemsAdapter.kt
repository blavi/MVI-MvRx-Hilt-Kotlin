package com.softvision.mvi_mvrx_hilt_kotlin.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.softvision.domain.model.base.ItemDetails
import com.softvision.domain.model.MovieDetails
import com.softvision.domain.model.TVShowDetails
import com.softvision.mvi_mvrx_hilt_kotlin.BuildConfig
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.ItemLayoutBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ItemsAdapter: RecyclerView.Adapter<ItemsAdapter.DataViewHolder>() {
    private var items: MutableList<ItemDetails> = mutableListOf()
    private val movieSelectSubject: PublishSubject<ItemDetails> = PublishSubject.create()
    val clickEvent: Observable<ItemDetails> = movieSelectSubject.hide()

    inner class DataViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemDetails) {
            when (item) {
                is MovieDetails -> {
                    item.updateItem(binding)
                }
                is TVShowDetails -> {
                    item.updateItem(binding)
                }
            }
//            (item as? TMDBMovieDetails)?.apply {
//                updateItem(binding)
//            }
//
//            (item as? TMDBTVShowDetails)?.apply {
//                updateItem(binding)
//            }
//            item.poster_path?.let {
//                val requestOptions = RequestOptions()
//                requestOptions.apply {
//                    placeholder(R.drawable.ic_baseline_hourglass_bottom_24)
//                    error(R.drawable.ic_baseline_mood_bad_24)
//                }
//                Glide.with(binding.itemBanner.context)
//                    .setDefaultRequestOptions(requestOptions)
//                    .load(Uri.parse(BuildConfig.IMAGE_BASE_URL + it))
//                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
//                    .into(binding.itemBanner)
//                }
//
//            binding.root.setOnClickListener {
//                movieSelectSubject.onNext(item)
////                clickListener(item)
//            }

//            Timber.i("Explore State: MOVIE- %s", item.title)
        }
    }

    private fun MovieDetails.updateItem(binding: ItemLayoutBinding) {
        poster_path?.let {
            val requestOptions = RequestOptions()
            requestOptions.apply {
                placeholder(R.drawable.ic_baseline_hourglass_bottom_24)
                error(R.drawable.ic_baseline_mood_bad_24)
            }
            Glide.with(binding.itemBanner.context)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(BuildConfig.IMAGE_BASE_URL + it))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(binding.itemBanner)
        }

        binding.root.setOnClickListener {
            movieSelectSubject.onNext(this)
//                clickListener(item)
        }
    }

    private fun TVShowDetails.updateItem(binding: ItemLayoutBinding) {
        poster_path?.let {
            val requestOptions = RequestOptions()
            requestOptions.apply {
                placeholder(R.drawable.ic_baseline_hourglass_bottom_24)
                error(R.drawable.ic_baseline_mood_bad_24)
            }
            Glide.with(binding.itemBanner.context)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.parse(BuildConfig.IMAGE_BASE_URL + it))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(binding.itemBanner)
        }

        binding.root.setOnClickListener {
            movieSelectSubject.onNext(this)
//                clickListener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(items[position])

    fun updateData(list: List<ItemDetails> = emptyList()) {
//        Timber.i("Explore State: MOVIES - notifydatasetchanged %s", list.size)
        items.clear()
        if (list.isNotEmpty()) {
            items.addAll(list)
            notifyDataSetChanged()
        }
    }
}