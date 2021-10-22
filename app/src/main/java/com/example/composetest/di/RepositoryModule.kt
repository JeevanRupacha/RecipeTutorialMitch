package com.example.composetest.di

import com.example.composetest.network.model.RecipeDtoMapper
import com.example.composetest.network.reponses.RecipeService
import com.example.composetest.repository.RecipeRepository
import com.example.composetest.repository.RecipeRepository_Imp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ) : RecipeRepository
    {
        return RecipeRepository_Imp(recipeService = recipeService ,mapper = recipeDtoMapper)
    }
}