package com.example.composetest.presentation.ui.recipe_list

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.composetest.components.BottomNav
import com.example.composetest.components.RecipeList
import com.example.composetest.components.SearchBar
import com.example.composetest.components.util.SnackbarController
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.example.composetest.presentation.ui.theme.AppTheme
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel,
    isDarkTheme: Boolean,
    onToggleTheme: ()-> Unit,
    snackbarController: SnackbarController,
    onNavToRecipeDetailScreen: (String) -> Unit,
){
    val recipes = viewModel.recipes.value
    val query = viewModel.query.value
    val selectedCategory = viewModel.selectedCategory.value
    val scaffoldState = rememberScaffoldState()
    val isLoading = viewModel.isLoading.value

    AppTheme(darkTheme = isDarkTheme) {

        Scaffold(
            topBar = {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    onToggleTheme = {onToggleTheme()},
                    searchRecipe = {
                        if (viewModel.selectedCategory.value?.value == "Milk") {
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldSate= scaffoldState,
                                    message = "Milk is in Snack",
                                    actionLabel = "Hide",
                                )
                            }
                        } else {
                            viewModel.onTriggerEvent(NewSearchEvent)
                        }
                    }
                )
            },
            bottomBar = {
                BottomNav(
                  onSearch = {},
                  onHome = {
                           viewModel.onQueryChange("")
                           viewModel.onTriggerEvent(NewSearchEvent)
                  },
                  onMoreVert = {}
                )
               },
            scaffoldState = scaffoldState,
            snackbarHost = {scaffoldState.snackbarHostState},
        ) {
            RecipeList(
                recipes = recipes,
                onListScrollPositionChange = viewModel::onListScrollPositionChange,
                isLoading = isLoading,
                page = viewModel.page.value,
                onTriggerEvent ={viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)} ,
                scaffoldState =scaffoldState,
                snackbarController = snackbarController,
                onNavToRecipeDetailScreen = onNavToRecipeDetailScreen,
            )
        }
    }
}