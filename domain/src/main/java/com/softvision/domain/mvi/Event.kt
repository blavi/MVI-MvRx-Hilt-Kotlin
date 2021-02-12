package com.softvision.domain.mvi

sealed class Event {
    object Load : Event()
    object Clear : Event()
}
