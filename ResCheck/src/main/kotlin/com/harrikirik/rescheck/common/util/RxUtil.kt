package com.harrikirik.rescheck.common.util

import io.reactivex.CompletableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun <T> observableSchedulers(): ObservableTransformer<T, T> {
    return ObservableTransformer {
        it.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> singleSchedulers(): SingleTransformer<T, T> {
    return SingleTransformer {
        it.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun completableSchedulers(): CompletableTransformer {
    return CompletableTransformer {
        it.subscribeOn(io.reactivex.schedulers.Schedulers.io()) .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> maybeSchedulers(): MaybeTransformer<T, T> {
    return MaybeTransformer {
        it.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun dispose(vararg disposables: Disposable?) {
    for (disposable in disposables) {
        disposable?.dispose()
    }
}

fun clearDisposable(disposable: CompositeDisposable?) {
    disposable?.clear()
}
