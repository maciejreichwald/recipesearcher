package com.rudearts.recipesearcher.domain.remote

import com.rudearts.recipesearcher.domain.model.Recipe
import io.reactivex.Single

interface RemoteCallable {

    fun loadRecipes():Single<List<Recipe>>

}