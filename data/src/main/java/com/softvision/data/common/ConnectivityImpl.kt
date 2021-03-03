package com.softvision.data.common

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectivityImpl @Inject constructor(private val context: Context) : Connectivity {

    override fun hasNetworkAccess(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.isConnected
    }
}