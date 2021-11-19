package com.example.composetest.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.domain.model.Recipe
import com.example.composetest.presentation.ui.recipe.RecipeDetailEvent.GetRecipeDetailEvent
import com.example.composetest.repository.RecipeRepository
import com.example.composetest.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
) : ViewModel()
{
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val isLoading = mutableStateOf(false)
    val onLoad = mutableStateOf(false)


    fun onTriggerEvent(detailEvent: RecipeDetailEvent)
    {
        Log.d(TAG, "onTriggerEvent1: c")
        viewModelScope.launch {
            try{
                when(detailEvent){
                    is GetRecipeDetailEvent -> {
                        if(recipe.value == null){
                            getRecipe(detailEvent.id)
                        }
                    }
                }

            }catch(e: Exception)
            {
                Log.d(TAG, "onTriggerEvent: $e , ${e.cause}")
                e.printStackTrace()
            }finally {
                Log.d(TAG, "onTriggerEvent: finally done !")
            }
        }
    }

    private suspend fun getRecipe(id: Int){
        isLoading.value = true
        delay(1000)
        val result = repository.get(token = token, id = id)
        this.recipe.value = result
        isLoading.value = false
    }

}