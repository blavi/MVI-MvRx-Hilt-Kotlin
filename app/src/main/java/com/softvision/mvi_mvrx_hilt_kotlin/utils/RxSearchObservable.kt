package com.softvision.mvi_mvrx_hilt_kotlin.utils

import androidx.appcompat.widget.SearchView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.PublishSubject

object RxSearchObservable {
    fun queryObservableFromView(searchView: SearchView): Observable<String> {
        val subject: PublishSubject<String> = PublishSubject.create()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                subject.onNext(text)
                searchView.clearFocus() // close keyboard
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return false
            }
        })
        return subject
    }

    fun closeObservableFromView(searchView: SearchView): Observable<Boolean> {
        val subject: PublishSubject<Boolean> = PublishSubject.create()

        searchView.setOnCloseListener {
            subject.onComplete()
            true
        }

        return subject
    }
}