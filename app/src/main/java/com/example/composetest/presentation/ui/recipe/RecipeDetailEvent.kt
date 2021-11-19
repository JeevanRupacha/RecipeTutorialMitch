package com.example.composetest.presentation.ui.recipe

sealed class RecipeDetailEvent{
    data class GetRecipeDetailEvent(val id: Int): RecipeDetailEvent()
}