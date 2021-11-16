package com.example.composetest.presentation.ui.recipe_list

sealed class RecipeListEvent{
    object NewSearchEvent: RecipeListEvent()
    object NextPageEvent: RecipeListEvent()
}
