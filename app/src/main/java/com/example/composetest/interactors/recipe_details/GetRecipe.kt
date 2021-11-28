package com.example.composetest.interactors.recipe_details

import android.view.contentcapture.DataRemovalRequest
import com.example.composetest.cache.RecipeDao
import com.example.composetest.cache.model.RecipeEntityMapper
import com.example.composetest.domain.data.DataState
import com.example.composetest.domain.model.Recipe
import com.example.composetest.network.model.RecipeDtoMapper
import com.example.composetest.network.reponses.RecipeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class GetRecipe(
    private val recipeDao: RecipeDao,
    private val entityMapper: RecipeEntityMapper,
    private val dtoMapper: RecipeDtoMapper,
    private val recipeService: RecipeService,
) {
    fun execute(
        token: String,
        recipeId: Int,
        isNetworkAvailable: Boolean,
    ) : Flow<DataState<Recipe>> = flow {
        try{
            emit(DataState.loading())

            val recipe = getRecipeFromCache(recipeId = recipeId)

            if(recipe != null)
            {
                emit(DataState.success(recipe))
            }else{

                if(isNetworkAvailable)
                {
                    val recipeFromNetwork = getRecipeFromNetwork(recipeId = recipeId, token = token)
                    recipeDao.insertRecipe(entityMapper.mapFromDomainModel(recipeFromNetwork))
                }

                val recipeFromCache = getRecipeFromCache(recipeId = recipeId)

                if(recipeFromCache != null)
                {
                    emit(DataState.success(recipeFromCache))
                }else{
                    throw Exception("Fail to get recipe from cache ")
                }
            }

        }catch (e: Exception)
        {
            emit(DataState.failure(e.message?: "Unknown error "))
        }
    }

    private suspend fun getRecipeFromCache(recipeId:Int): Recipe?
    {
        return recipeDao.getRecipeById(recipeId)?.let { recipeEntity ->
            entityMapper.mapToDomainModel(recipeEntity)
        }
    }

    private suspend fun getRecipeFromNetwork(recipeId: Int, token: String): Recipe
    {
        return dtoMapper.mapToDomainModel(recipeService.get(token = token, id= recipeId))
    }
}