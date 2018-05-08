package com.rudearts.recipesearcher.data.remote

import com.rudearts.recipesearcher.data.model.remote.RecipesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RestApi {

    @GET("test35")
    fun getRecipes(): Single<RecipesResponse>

}