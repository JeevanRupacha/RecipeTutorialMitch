package com.example.composetest.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetest.components.util.SnackbarController
import com.example.composetest.presentation.navigation.Screen
import com.example.composetest.presentation.ui.recipe.RecipeDetailScreen
import com.example.composetest.presentation.ui.recipe.RecipeDetailViewModel
import com.example.composetest.presentation.ui.recipe_list.RecipeListScreen
import com.example.composetest.presentation.ui.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.RecipeList.route){

                composable(Screen.RecipeList.route){

                    val viewModel = hiltViewModel<RecipeListViewModel>()
                    val snackbarController: SnackbarController = SnackbarController(lifecycleScope)

                    RecipeListScreen(
                        viewModel = viewModel,
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        onToggleTheme = (application as BaseApplication)::toggleLightTheme ,
                        snackbarController = snackbarController,
                        onNavToRecipeDetailScreen = navController::navigate,
                    )
                }

                composable(
                    route = Screen.RecipeDetail.route + "/{userID}",
                    arguments = listOf(navArgument("userID"){type = NavType.IntType})
                ){  navBackStackEntry ->

                    val viewModel = hiltViewModel<RecipeDetailViewModel>()
                    RecipeDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        viewModel = viewModel,
                        recipeId = navBackStackEntry.arguments?.getInt("userID"),
                    )
                }


            }
        }

    }
}
