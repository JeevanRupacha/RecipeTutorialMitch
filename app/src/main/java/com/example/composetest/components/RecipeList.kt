package com.example.composetest.components

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavController
import com.example.composetest.R
import com.example.composetest.components.util.SnackbarController
import com.example.composetest.domain.model.Recipe
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.composetest.util.FOOD_API_PAGE_SIZE
import com.example.composetest.util.TAG

@ExperimentalComposeUiApi
@Composable
fun RecipeList(
    recipes: List<Recipe>,
    onListScrollPositionChange: (Int) -> Unit,
    isLoading: Boolean,
    page: Int,
    onTriggerEvent: (NextPageEvent) -> Unit,
    scaffoldState: ScaffoldState,
    navController: NavController,
    snackbarController: SnackbarController,
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
                    recipe.id?.let{ rid ->
                        val bundle = Bundle()
                        bundle.putInt("recipe_id", rid)
                        navController.navigate(R.id.action_recipeListFragment_to_recipeFragment, bundle)
                    }
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