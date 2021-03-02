package com.softvision.mvi_mvrx_hilt_kotlin.utils

import androidx.appcompat.widget.SearchView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object RxSearchObservable {
    fun fromView(searchView: SearchView): Observable<String> {
        val subject: PublishSubject<String> = PublishSubject.create()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                subject.onNext(text)
                searchView.clearFocus() //if you want to close keyboard
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return false
            }
        })
        return subject
    }
}