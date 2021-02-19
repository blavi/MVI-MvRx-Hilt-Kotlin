package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState

open class BaseViewModel<S: MvRxState>(state: S): BaseMvRxViewModel<MvRxState>(state) {
}