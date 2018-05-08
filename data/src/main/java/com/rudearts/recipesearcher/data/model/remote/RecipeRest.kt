package com.rudearts.recipesearcher.data.model.remote

import com.google.gson.annotations.SerializedName

data class RecipeRest(
        val title:String?,
        val description:String?,
        val orderId:Long?,
        @SerializedName("image_url") val imageUrl:String?)