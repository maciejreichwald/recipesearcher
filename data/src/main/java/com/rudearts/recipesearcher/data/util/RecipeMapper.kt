package com.rudearts.recipesearcher.data.util

import android.text.TextUtils
import com.rudearts.recipesearcher.data.model.greendao.RecipeDb
import com.rudearts.recipesearcher.data.model.remote.RecipeRest
import com.rudearts.recipesearcher.domain.model.Recipe
import java.util.*
import javax.inject.Inject

class RecipeMapper @Inject constructor() {

    companion object {
        private const val LINK_PHRASE = "http"
    }

    fun db2recipe(recipe: RecipeDb) = with(recipe) {
        Recipe(id, title, description, imageUrl, contentUrl)
    }

    fun rest2recipe(recipe: RecipeRest) = with(recipe) {
        Recipe(nonNull(orderId), parseTitle(title, description), parseDescription(description), imageUrl, parseContentUrl(description))
    }

    fun recipe2db(recipe:Recipe) = with(recipe) {
        RecipeDb(id, title, description, imageUrl, contentUrl)
    }

    private fun parseContentUrl(description: String?) = description?.let {
        val index = description.indexOf(LINK_PHRASE)
        when(index) {
            in -1..0 -> null
            else -> description.substring(index)
        }
    }

    private fun parseDescription(description: String?): String {
        description?.let{
            val index = description.indexOf(LINK_PHRASE)
            return when(index) {
                in -1..0 -> description
                else -> description.substring(0, index)
            }
        }
        return ""
    }

    private fun parseTitle(title: String?, description: String?): String {
        if (title?.isNotEmpty() == true) return title
        if (TextUtils.isEmpty(description)) return ""

        val words = description?.split(" ") ?: arrayListOf("")
        var titleReplacement = words[0]
        if (words.size > 1) titleReplacement += " ${words[1]}"

        return titleReplacement
    }

    private fun nonNull(value: Long?) = value ?: Random().nextLong()

}