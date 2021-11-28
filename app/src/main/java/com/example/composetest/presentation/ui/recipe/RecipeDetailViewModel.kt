package com.example.composetest.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.domain.model.Recipe
import com.example.composetest.interactors.recipe_details.GetRecipe
import com.example.composetest.presentation.ui.recipe.RecipeDetailEvent.GetRecipeDetailEvent
import com.example.composetest.presentation.ui.util.DialogQueue
import com.example.composetest.presentation.util.NetworkConnectionManager
import com.example.composetest.repository.RecipeRepository
import com.example.composetest.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.Exception

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipe: GetRecipe,
    private val networkConnectionManager: NetworkConnectionManager,
    @Named("auth_token") private val token: String,
) : ViewModel()
{
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val isLoading = mutableStateOf(false)
    val onLoad = mutableStateOf(false)
    val dialogQueue = DialogQueue()


    fun onTriggerEvent(detailEvent: RecipeDetailEvent)
    {
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

    private fun getRecipe(id: Int){

        getRecipe.execute(
            isNetworkAvailable = networkConnectionManager.isInternetAvail.value,
            token = token,
            recipeId = id
        ).onEach { dataState ->
            isLoading.value = dataState.loading

            dataState.data?.let {
                recipe.value = it
            }

            dataState.error?.let {
                dialogQueue.appendErrorMessage("Error", dataState.error)
                throw Exception("Error ")
            }

        }.launchIn(viewModelScope)

    }

}