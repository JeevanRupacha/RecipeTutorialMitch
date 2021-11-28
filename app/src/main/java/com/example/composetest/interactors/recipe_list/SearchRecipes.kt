package com.example.composetest.interactors.recipe_list

import com.example.composetest.cache.RecipeDao
import com.example.composetest.cache.model.RecipeEntityMapper
import com.example.composetest.domain.data.DataState
import com.example.composetest.domain.model.Recipe
import com.example.composetest.network.model.RecipeDtoMapper
import com.example.composetest.network.reponses.RecipeService
import com.example.composetest.util.FOOD_API_PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchRecipes(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val recipeDtoMapper: RecipeDtoMapper,
    private val recipeEntityMapper: RecipeEntityMapper,
) {
    fun execute(
        query: String,
        token: String,
        page: Int,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<List<Recipe>>> = flow {
        try{
            emit(DataState.loading())

            // To simulate loading bar
            delay(1000)

            //simulate error
            if(query == "error")
            {
                throw Exception("Error simulated occured ")
            }

            if(isNetworkAvailable)
            {
               val recipes: List<Recipe> = getRecipeFromNetwork(token = token, page = page, query=query)

               recipeDao.insertRecipes(recipes = recipeEntityMapper.toEntityList(recipes))
            }

            val recipeFromCache: List<Recipe> = if(query.isBlank())
            {
               recipeEntityMapper.fromEntityList(
                   recipeDao.getAllRecipes(page,
                       FOOD_API_PAGE_SIZE
                   )
               )
            }else{
               recipeEntityMapper.fromEntityList(
                   recipeDao.searchRecipes(
                       query = query,
                       page = page,
                       pageSize = FOOD_API_PAGE_SIZE
                   )
               )
            }

            emit(DataState.success(recipeFromCache))

        }catch (e: Exception)
        {
            emit(DataState.failure(e.message?:"Unknown Error "))
        }
    }

    private suspend fun getRecipeFromNetwork(
        token: String,
        page:Int,
        query: String,
    ): List<Recipe>{
        return recipeDtoMapper.toDomainList(recipeService.search(
            token = token,
            query = query,
            page = page,
        ).results
        )
    }

}