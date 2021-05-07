package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MvRxState

open class BaseViewModel<S: MavericksState>(state: S): BaseMvRxViewModel<S>(state)