package com.example.composetest.network.reponses

import com.example.composetest.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int ,

    @SerializedName("results")
    var results : List<RecipeDto>

)