package com.example.composetest.presentation.ui.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.composetest.presentation.components.CircularIndeterminateProgressBar
import com.example.composetest.presentation.components.recipedetailscreen.RecipeView
import com.example.composetest.presentation.ui.theme.AppTheme

@ExperimentalComposeUiApi
@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    viewModel: RecipeDetailViewModel,
    recipeId: Int?
)
{
    if(recipeId == null) return
    val onLoad = viewModel.onLoad.value
    val dialogQueue = viewModel.dialogQueue

    AppTheme(
        dialogQueue = dialogQueue.queue.value,
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
    ) {

        if(!onLoad)
        {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(RecipeDetailEvent.GetRecipeDetailEvent(recipeId))
        }

//        Log.d(TAG, "loading $isLoading")

        val recipe = viewModel.recipe.value
        val isLoading = viewModel.isLoading.value
        val scaffoldState: ScaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState
        ) {
            Box(modifier = Modifier.fillMaxSize())
            {
                recipe?.let{ recipe ->
                    RecipeView(recipe = recipe)
                }

                CircularIndeterminateProgressBar(display = isLoading, 0.5f)
            }

        }

    }
}