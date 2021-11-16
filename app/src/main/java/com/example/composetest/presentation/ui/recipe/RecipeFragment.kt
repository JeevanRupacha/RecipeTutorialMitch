package com.example.composetest.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composetest.components.CircularIndeterminateProgressBar
import com.example.composetest.components.recipefragment.RecipeView
import com.example.composetest.presentation.BaseApplication
import com.example.composetest.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.example.composetest.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipe_id")?.let{ rId ->
            viewModel.onTriggerEvent(GetRecipeEvent(rId))
        }
    }

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(
                    darkTheme = application.isDark.value
                ) {
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

        }
    }
}