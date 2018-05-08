package com.rudearts.recipesearcher.domain.usecase

import com.rudearts.recipesearcher.domain.model.Recipe
import com.rudearts.recipesearcher.domain.remote.DatabaseCache
import com.rudearts.recipesearcher.domain.remote.RemoteCallable
import io.reactivex.Single
import javax.inject.Inject

class LoadRecipesUseCase @Inject constructor(
        private val remoteCallable: RemoteCallable,
        private val database: DatabaseCache) {

    fun load():Single<List<Recipe>> = when(database.hasRecipes()) {
        true -> database.loadRecipes()
        else -> remoteCallable.loadRecipes()
    }

}