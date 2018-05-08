package com.rudearts.recipesearcher.extentions

import android.app.Activity
import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.rudearts.recipesearcher.GithubBrowserApplication

val Activity.app: GithubBrowserApplication get() = application as GithubBrowserApplication

fun AppCompatActivity.getAppInjector() = app.appComponent

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}