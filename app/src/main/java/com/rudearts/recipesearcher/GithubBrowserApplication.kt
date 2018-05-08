package com.rudearts.recipesearcher

import android.app.Application
import com.rudearts.recipesearcher.di.component.AppComponent
import com.rudearts.recipesearcher.di.component.DaggerAppComponent
import com.rudearts.recipesearcher.di.module.AppModule

class GithubBrowserApplication : Application() {

    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()

        initInjector()
    }

    private fun initInjector() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}