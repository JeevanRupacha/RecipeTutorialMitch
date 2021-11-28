package com.example.composetest.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.composetest.presentation.components.util.SnackbarController
import com.example.composetest.domain.model.Recipe
import com.example.composetest.presentation.navigation.Screen
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.composetest.util.FOOD_API_PAGE_SIZE

@ExperimentalComposeUiApi
@Composable
fun RecipeList(
    recipes: List<Recipe>,
    onListScrollPositionChange: (Int) -> Unit,
    isLoading: Boolean,
    page: Int,
    onTriggerEvent: (NextPageEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    onNavToRecipeDetailScreen: (String) -> Unit,
)
{
    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize())
    {
        LazyColumn {
            itemsIndexed(
                items = recipes
            ) { index, recipe ->
                onListScrollPositionChange(index)
                if((index + 1) >= (page * FOOD_API_PAGE_SIZE) && !isLoading)
                {
                    onTriggerEvent(NextPageEvent)
                }
                RecipeCard(recipe = recipe, onClick = {
                    val route = Screen.RecipeDetail.route +"/${recipe.id}"
                    onNavToRecipeDetailScreen(route)
                })

            }
        }

        CircularIndeterminateProgressBar(display = isLoading, .3f)

        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()},
            modifier = Modifier.align(Alignment.Center)
        )
    }

}