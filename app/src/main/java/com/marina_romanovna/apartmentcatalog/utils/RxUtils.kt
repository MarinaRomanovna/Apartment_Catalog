package com.marina_romanovna.apartmentcatalog.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

fun <T : Any> Single<T>.ioMain(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T : Any> Single<T>.get(
    disposable: CompositeDisposable,
    onError: (Throwable) -> Unit = logError,
    onSuccess: (T) -> Unit = { },
): Unit = ioMain().subscribe(onSuccess, onError).addTo(disposable)

fun Completable.ioMain(): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.get(
    disposable: CompositeDisposable,
    onError: (Throwable) -> Unit = logError,
    onComplete: () -> Unit = { },
): Unit = ioMain().subscribe(onComplete, onError).addTo(disposable)

private fun Disposable.addTo(disposable: CompositeDisposable) {
    disposable.add(this)
}

private val logError: (Throwable) -> Unit = { Timber.e(it) }