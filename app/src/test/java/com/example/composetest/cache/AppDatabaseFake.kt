package com.example.composetest.cache

import com.example.composetest.cache.model.RecipeEntity

class AppDatabaseFake {
    val recipes = mutableListOf<RecipeEntity>()
}