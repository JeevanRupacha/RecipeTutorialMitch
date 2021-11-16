package com.example.composetest.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.composetest.components.*
import com.example.composetest.components.util.SnackbarController
import com.example.composetest.presentation.ui.theme.AppTheme
import com.example.composetest.util.FOOD_API_PAGE_SIZE
import com.example.composetest.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    private val snackbarController = SnackbarController(lifecycleScope)

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = viewModel.isDarkTheme.value) {

                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val scaffoldState = rememberScaffoldState()
                    val isLoading = viewModel.isLoading.value

                    Scaffold(
                        topBar = {
                            SearchBar(
                                query = query,
                                onQueryChange = viewModel::onQueryChange,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = viewModel::onToggleTheme,
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
                                        viewModel.searchRecipe()
                                    }
                                }
                            )
                        },
                        bottomBar = { BottomNav() },
                        scaffoldState = scaffoldState,
                        snackbarHost = {scaffoldState.snackbarHostState},
                    ) {

                        Box(modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxSize())
                        {
                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ) { index, recipe ->
                                    viewModel.onListScrollPositionChange(index)
                                    if((index + 1) >= (viewModel.page.value * FOOD_API_PAGE_SIZE) && !isLoading)
                                    {
                                        viewModel.nextPage()
                                    }
                                    RecipeCard(recipe = recipe, onClick = {})

                                    Log.d(TAG, "index : $index")
                                }
                            }

                            CircularIndeterminateProgressBar(display = isLoading)

                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()},
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNav()
{
    BottomNavigation(
        elevation = 15.dp,
        modifier = Modifier.zIndex(5f)
    ) {
        BottomNavigationItem(
            icon = {Icon(imageVector = Icons.Filled.Search, contentDescription = "search icon", tint = Color.White)},
            selected = false,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = {Icon(imageVector = Icons.Filled.Home, contentDescription = "search icon", tint = Color.White)},
            selected = true,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = {Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "search icon", tint = Color.White)},
            selected = false,
            onClick = { /*TODO*/ }
        )
    }
}