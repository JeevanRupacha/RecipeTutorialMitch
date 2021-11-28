package com.example.composetest.di

import com.example.composetest.cache.RecipeDao
import com.example.composetest.cache.model.RecipeEntityMapper
import com.example.composetest.interactors.recipe_details.GetRecipe
import com.example.composetest.interactors.recipe_list.SearchRecipes
import com.example.composetest.network.model.RecipeDtoMapper
import com.example.composetest.network.reponses.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule{

    @ViewModelScoped
    @Provides
    fun provideSearchRecipes(
        recipeDao: RecipeDao,
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper,
        recipeEntityMapper: RecipeEntityMapper,
    ): SearchRecipes {
        return SearchRecipes(
            recipeDao = recipeDao,
            recipeService = recipeService,
            recipeDtoMapper = recipeDtoMapper,
            recipeEntityMapper = recipeEntityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetRecipe(
        recipeDao: RecipeDao,
        entityMapper: RecipeEntityMapper,
        dtoMapper: RecipeDtoMapper,
        recipeService: RecipeService,
    ): GetRecipe
    {
        return GetRecipe(
            recipeDao = recipeDao,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper,
            recipeService = recipeService,
        )
    }
}