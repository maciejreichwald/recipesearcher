package com.rudearts.recipesearcher.di.component

import com.rudearts.recipesearcher.di.module.*
import com.rudearts.recipesearcher.ui.location.DetailsActivity
import com.rudearts.recipesearcher.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (RemoteModule::class), (DbModule::class), (ViewModelModule::class)])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: DetailsActivity)
}