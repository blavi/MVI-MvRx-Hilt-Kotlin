package com.softvision.data.network.base

interface RetrofitResponse<T : Any> {
    fun getContent(): List<T>
}