package com.rudearts.recipesearcher.data.database

import com.rudearts.recipesearcher.data.model.greendao.DaoSession
import com.rudearts.recipesearcher.data.util.RecipeMapper
import com.rudearts.recipesearcher.domain.model.Recipe
import com.rudearts.recipesearcher.domain.remote.DatabaseCache
import io.reactivex.Single
import javax.inject.Inject

class DatabaseStorage @Inject constructor(
        private val session: DaoSession,
        private val mapper: RecipeMapper) : DatabaseCache {

    override fun loadRecipes():Single<List<Recipe>> = Single.fromCallable { session.recipeDbDao.loadAll().requireNoNulls() }
            .map { items -> items.map { item -> mapper.db2recipe(item) }}

    override fun hasRecipes(): Boolean = session.recipeDbDao.count() > 0

    override fun saveRecipes(recipes: List<Recipe>) {
        session.recipeDbDao.insertOrReplaceInTx(
                recipes.map { recipe -> mapper.recipe2db(recipe) }
        )
    }
}