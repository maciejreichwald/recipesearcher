package com.rudearts.recipesearcher.di.module

import com.rudearts.recipesearcher.BuildConfig
import com.rudearts.recipesearcher.data.remote.RemoteCalls
import com.rudearts.recipesearcher.data.remote.RestApi
import com.rudearts.recipesearcher.data.util.RecipeMapper
import com.rudearts.recipesearcher.domain.remote.DatabaseCache
import com.rudearts.recipesearcher.domain.remote.RemoteCallable
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    companion object {
        private const val BASE_URL = "http://pinky.futuremind.com/~dpaluch/"
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun provideRestApi(retrofit: Retrofit) = retrofit.create(RestApi::class.java) as RestApi

    @Provides
    fun provideRemoteCallable(restApi: RestApi,
                              database:DatabaseCache,
                              mapper: RecipeMapper) = RemoteCalls(restApi, database, mapper) as RemoteCallable
}