package com.example.composetest.presentation.navigation

sealed class Screen(
    val route: String,
){
    object RecipeList: Screen("recipe_list")
    object RecipeDetail: Screen("recipe_detail")
}