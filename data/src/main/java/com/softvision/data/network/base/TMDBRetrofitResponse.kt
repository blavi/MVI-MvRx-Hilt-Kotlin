package com.softvision.data.network.base

interface TMDBRetrofitResponse<T : Any> {
    fun getContent(): List<T>
}