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
import androidx.navigation.findNavController
import com.example.composetest.components.*
import com.example.composetest.components.util.SnackbarController
import com.example.composetest.presentation.BaseApplication
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.example.composetest.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import com.example.composetest.presentation.ui.theme.AppTheme
import com.example.composetest.util.FOOD_API_PAGE_SIZE
import com.example.composetest.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

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

                AppTheme(darkTheme = application.isDark.value) {

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
                                onToggleTheme = application::toggleLightTheme,
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
                        bottomBar = { BottomNav() },
                        scaffoldState = scaffoldState,
                        snackbarHost = {scaffoldState.snackbarHostState},
                    ) {
                        RecipeList(
                            recipes = recipes,
                            onListScrollPositionChange = viewModel::onListScrollPositionChange,
                            isLoading = isLoading,
                            page = viewModel.page.value,
                            onTriggerEvent ={viewModel.onTriggerEvent(NextPageEvent)} ,
                            scaffoldState =scaffoldState,
                            navController = findNavController(),
                            snackbarController = snackbarController
                        )
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