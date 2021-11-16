package com.example.composetest.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.domain.model.Recipe
import com.example.composetest.repository.RecipeRepository
import com.example.composetest.util.FOOD_API_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        searchRecipe()
    }

    fun searchRecipe()
    {
        viewModelScope.launch {
            isLoading.value = true
            resetSearchState()

            delay(1000)
            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result

            Log.d("RecipeViewModel", "searchRecipe: $result")

            isLoading.value = false
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

    fun nextPage()
    {
        viewModelScope.launch {
            if((listScrollPosition + 1) >= (FOOD_API_PAGE_SIZE * page.value))
            {
                incrementPage()
                isLoading.value = true
                delay(1000)
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