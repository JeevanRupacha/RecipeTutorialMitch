package com.example.composetest.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetest.datastore.SettingsDatastore
import com.example.composetest.presentation.components.util.SnackbarController
import com.example.composetest.presentation.navigation.Screen
import com.example.composetest.presentation.ui.recipe.RecipeDetailScreen
import com.example.composetest.presentation.ui.recipe.RecipeDetailViewModel
import com.example.composetest.presentation.ui.recipe_list.RecipeListScreen
import com.example.composetest.presentation.ui.recipe_list.RecipeListViewModel
import com.example.composetest.presentation.util.NetworkConnectionManager
import com.example.composetest.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    @Inject
    lateinit var settingsDataStore: SettingsDatastore

    override fun onStart() {
        super.onStart()
        networkConnectionManager.registerConnectionObserver(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectionManager.unregisterConnectionObserver(this)
    }

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ${networkConnectionManager.isInternetAvail.value}")
        setContent {

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.RecipeList.route){

                composable(Screen.RecipeList.route){

                    val viewModel = hiltViewModel<RecipeListViewModel>()
                    val snackbarController: SnackbarController = SnackbarController(lifecycleScope)

                    RecipeListScreen(
                        viewModel = viewModel,
                        isNetworkAvailable = networkConnectionManager.isInternetAvail.value,
                        isDarkTheme = settingsDataStore.isDarkTheme.value,
                        onToggleTheme = settingsDataStore::toggleTheme ,
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
                        isNetworkAvailable = networkConnectionManager.isInternetAvail.value,
                        isDarkTheme = settingsDataStore.isDarkTheme.value,
                        viewModel = viewModel,
                        recipeId = navBackStackEntry.arguments?.getInt("userID"),
                    )
                }

            }
        }
    }
}