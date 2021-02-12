package com.softvision.mvi_mvrx_hilt_kotlin.viewmodel

import com.airbnb.mvrx.BaseMvRxViewModel
import com.softvision.domain.mvi.ExplorerState

open class BaseViewModel<S: ExplorerState>(state: S): BaseMvRxViewModel<ExplorerState>(state) {
}