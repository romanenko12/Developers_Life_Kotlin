package com.example.developerslifekotlin.utils

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseRxViewModel : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun Disposable.disposeOnFinish(): Disposable {
        disposable.add(this)
        return this
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
