package com.example.composetest.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDto (

    @SerializedName("pk")
    var pk: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("publisher")
    var publisher: String,

    @SerializedName("rating")
    var rating: Int = 0,

    @SerializedName("source_url")
    var sourceURl: String,

//    @SerializedName("description")
//    var description: String,
//
    @SerializedName("ingredients")
    var ingredients: List<String> = emptyList(),

//    @SerializedName("cooking_instructions")
//    var cookingInstructions: String,

    @SerializedName("long_date_added")
    var longDateAdded: Long,

    @SerializedName("long_date_updated")
    var longDateUpdated: Long,

    @SerializedName("featured_image")
    var featuredImage: String
)