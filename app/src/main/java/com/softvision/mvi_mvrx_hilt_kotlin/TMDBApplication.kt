package com.softvision.mvi_mvrx_hilt_kotlin

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.softvision.data.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class TMDBApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(applicationContext)
//        MvRx.viewModelConfigFactory = MvRxViewModelConfigFactory(applicationContext)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
//            Timber.plant(object : DebugTree() {
//                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//                    super.log(priority, "Explore State", message, t)
//                }
//            })
        }
    }
}