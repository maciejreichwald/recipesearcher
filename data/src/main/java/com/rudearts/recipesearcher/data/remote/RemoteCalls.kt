package com.rudearts.recipesearcher.data.remote

import com.rudearts.recipesearcher.data.util.RecipeMapper
import com.rudearts.recipesearcher.domain.model.Recipe
import com.rudearts.recipesearcher.domain.remote.DatabaseCache
import com.rudearts.recipesearcher.domain.remote.RemoteCallable
import io.reactivex.Single
import javax.inject.Inject

class RemoteCalls @Inject constructor(
        private val restApi: RestApi,
        private val database: DatabaseCache,
        private val mapper: RecipeMapper) : RemoteCallable {

    override fun loadRecipes(): Single<List<Recipe>> = restApi.getRecipes()
            .map { response ->
                response.data?.requireNoNulls()?.map { recipe -> mapper.rest2recipe(recipe) }
                        ?: emptyList()
            }
            .doAfterSuccess { recipes -> database.saveRecipes(recipes) }
}