package com.softvision.mvi_mvrx_hilt_kotlin.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.softvision.domain.model.BaseItemDetails
import com.softvision.domain.model.GenreDetails
import timber.log.Timber


class GenresAdapter(
    context: Context,
    layout: Int,
    private var genresList: MutableList<BaseItemDetails>
) : ArrayAdapter<BaseItemDetails>(context, layout, genresList) {
    override fun getCount(): Int {
        return genresList.size
    }

    override fun getItem(position: Int): BaseItemDetails {
        return genresList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getPositionById(genreId: Int): Int {
        return getPosition(
            genresList.find {
                (it as? GenreDetails)?.id == genreId
            }
        )
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        setGenre(view, getItem(position))
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        setGenre(view, getItem(position))
        return view
    }

    fun addData(list: List<BaseItemDetails>) {
        Timber.i("Explore State: Genres - notifydatasetchanged")
        genresList.clear()
        genresList.addAll(list)
        notifyDataSetChanged()
    }

    private fun setGenre(view: TextView, genreDetails: BaseItemDetails) {
        (genreDetails as? GenreDetails)?.let {
            view.text = it.name
        }
    }
}