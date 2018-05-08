package com.rudearts.recipesearcher.di.module

import android.content.Context
import com.rudearts.recipesearcher.GithubBrowserApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: GithubBrowserApplication) {

    @Provides
    @Singleton
    fun provideApplication(): GithubBrowserApplication = application

    @Provides
    fun provideContext(): Context = application
}
