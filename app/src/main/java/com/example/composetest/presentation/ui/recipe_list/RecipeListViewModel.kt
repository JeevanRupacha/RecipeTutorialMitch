package com.example.composetest.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.domain.model.Recipe
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import com.example.composetest.repository.RecipeRepository
import com.example.composetest.util.FOOD_API_PAGE_SIZE
import com.example.composetest.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
        private val repository: RecipeRepository,
        @Named("auth_token") private val token: String
    ): ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val isLoading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var listScrollPosition = 0

    //implement with jetpack datastore
    val isDarkTheme = mutableStateOf(false)

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

    private suspend fun searchRecipe()
    {
        viewModelScope.launch {
            isLoading.value = true
            resetSearchState()

            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result

            isLoading.value = false
        }
    }

    private suspend fun nextPage()
    {
        viewModelScope.launch {
            if((listScrollPosition + 1) >= (FOOD_API_PAGE_SIZE * page.value))
            {
                incrementPage()
                isLoading.value = true
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )

                appendRecipeList(result)
                isLoading.value = false
            }
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

    //To implement with jetpack datastore
    fun onToggleTheme()
    {
        isDarkTheme.value = !isDarkTheme.value
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

    fun getRepo() = repository
    fun getToken() = token
}