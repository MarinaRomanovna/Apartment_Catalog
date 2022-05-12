package com.marina_romanovna.apartmentcatalog.utils

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel: ViewModel() {

    protected val disposables = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable){
        disposables.addAll(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}