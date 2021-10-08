package com.example.composetest.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDto (

    @SerializedName("pk")
    var pk: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("publisher")
    var publisher: String? = null,

    @SerializedName("rating")
    var rating: Int? = 0,

    @SerializedName("sourceURl")
    var sourceURl: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("ingredients")
    var ingredients: List<String>? = null,

    @SerializedName("cooking_instructions")
    var cookingInstructions: String? = null,

    @SerializedName("date_added")
    var dateAdded: String? = null,

    @SerializedName("date_updated")
    var dateUpdated: String? = null,

    @SerializedName("featured_image")
    var featuredImage: String? = null
)