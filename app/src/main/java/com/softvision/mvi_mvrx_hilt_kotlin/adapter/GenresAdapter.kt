package com.softvision.mvi_mvrx_hilt_kotlin.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.softvision.domain.model.TMDBGenre
import timber.log.Timber


class GenresAdapter(context: Context, layout: Int, private var genresList: MutableList<TMDBGenre>): ArrayAdapter<TMDBGenre>(context, layout, genresList) {
    override fun getCount(): Int {
        return genresList.size
    }

    override fun getItem(position: Int): TMDBGenre {
        return genresList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
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

    fun addData(list: List<TMDBGenre>) {
        Timber.i("Explore State: MOVIES - trending notifydatasetchanged")
        genresList.clear()
        genresList.addAll(list)
        notifyDataSetChanged()
    }

    private fun setGenre(view: TextView, genre: TMDBGenre) {
        view.text = genre.name
    }
}