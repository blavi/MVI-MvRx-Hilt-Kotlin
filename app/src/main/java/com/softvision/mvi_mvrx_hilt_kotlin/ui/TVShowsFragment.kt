package com.softvision.mvi_mvrx_hilt_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.softvision.mvi_mvrx_hilt_kotlin.R
import com.softvision.mvi_mvrx_hilt_kotlin.viewmodel.TVShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVShowsFragment : Fragment() {

    private lateinit var TVShowsViewModel: TVShowsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        TVShowsViewModel =
                ViewModelProvider(this).get(TVShowsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tv_shows, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        TVShowsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}