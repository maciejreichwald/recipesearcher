package com.rudearts.recipesearcher.extentions

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.threadToAndroid(): Single<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.threadToIO(): Single<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
}

fun <T> Observable<T>.threadToAndroid(): Observable<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.threadToIO(): Observable<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
}