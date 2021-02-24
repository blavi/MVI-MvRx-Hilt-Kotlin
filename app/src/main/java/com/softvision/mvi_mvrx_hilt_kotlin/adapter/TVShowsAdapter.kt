package com.softvision.mvi_mvrx_hilt_kotlin.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.softvision.domain.model.TMDBMovieDetails
import com.softvision.domain.model.TMDBTVShowDetails
import com.softvision.mvi_mvrx_hilt_kotlin.BuildConfig
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.databinding.ItemLayoutBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class TVShowsAdapter: RecyclerView.Adapter<TVShowsAdapter.DataViewHolder>() {
    private var items: MutableList<TMDBTVShowDetails> = mutableListOf()
    private val tvShowSelectSubject: PublishSubject<TMDBTVShowDetails> = PublishSubject.create()
    val clickEvent: Observable<TMDBTVShowDetails> = tvShowSelectSubject.hide()

    inner class DataViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TMDBTVShowDetails) {
            item.poster_path?.let {
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

            binding.root.setOnClickListener() {
                tvShowSelectSubject.onNext(item)
//                clickListener(item)
            }

//            Timber.i("Explore State: TV SHOW - %s", item.title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(items[position])

    fun addData(list: List<TMDBTVShowDetails>) {
//        Timber.i("Explore State: TV SHOWS - trending notifydatasetchanged")
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}