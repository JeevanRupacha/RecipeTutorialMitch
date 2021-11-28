package com.example.composetest.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.domain.model.Recipe
import com.example.composetest.interactors.recipe_list.SearchRecipes
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import com.example.composetest.presentation.ui.util.DialogQueue
import com.example.composetest.presentation.util.NetworkConnectionManager
import com.example.composetest.repository.RecipeRepository
import com.example.composetest.util.FOOD_API_PAGE_SIZE
import com.example.composetest.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.Exception

@HiltViewModel
class RecipeListViewModel @Inject constructor(
        private val searchRecipes: SearchRecipes,
        private val networkConnectionManager: NetworkConnectionManager,
        @Named("auth_token") private val token: String
    ): ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val isLoading = mutableStateOf(false)
    val page = mutableStateOf(1)
    val dialogQueue = DialogQueue()
    private var listScrollPosition = 0

    init{ 
        onTriggerEvent(NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent)
    {
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {searchRecipe()}
                    is NextPageEvent -> {nextPage()}
                }
            }catch(e: Exception)
            {
                Log.d(TAG, "onTriggerEvent: $e , $${e.cause}")
                e.printStackTrace()
            }finally {
                Log.d(TAG, "onTriggerEvent: Event Trigger is done !")
            }
        }
    }

    private fun searchRecipe()
    {
            resetSearchState()

            searchRecipes.execute(
                query = query.value,
                token = token,
                page = page.value,
                isNetworkAvailable = networkConnectionManager.isInternetAvail.value,
            ).onEach { dataState ->

                isLoading.value = dataState.loading

                dataState.data?.let { data ->
                    recipes.value = data
                }

                dataState.error?.let { error ->
                    dialogQueue.appendErrorMessage("Error", error)
                    Log.d(TAG, "searchRecipe 1: $error")
                }


            }.launchIn(viewModelScope)

    }

    private fun nextPage()
    {

        if((listScrollPosition + 1) >= (FOOD_API_PAGE_SIZE * page.value)) {
            incrementPage()
            searchRecipes.execute(
                query = query.value,
                token = token,
                page = page.value,
                isNetworkAvailable = true,
            ).onEach { dataState ->

                isLoading.value = dataState.loading

                dataState.data?.let { recipes ->
                    appendRecipeList(recipes)
                }

                dataState.error?.let { error ->
                    dialogQueue.appendErrorMessage("Error", error)
                }


            }.launchIn(viewModelScope)
        }
    }


    private fun resetSearchState()
    {
        recipes.value = listOf()
        page.value = 1
        onListScrollPositionChange(0)
        if(selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory()
    {
        selectedCategory.value = null
    }

    fun onListScrollPositionChange(position: Int)
    {
        listScrollPosition = position
    }

    private fun incrementPage()
    {
        page.value += 1
    }

    private fun appendRecipeList(recipes: List<Recipe>)
    {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    fun onQueryChange(query: String ){
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String)
    {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChange(category)
    }
}