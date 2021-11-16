package com.example.composetest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.composetest.presentation.ui.recipe_list.FoodCategory
import com.example.composetest.presentation.ui.recipe_list.getAllFoodCategory

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    searchRecipe: () -> Unit,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged : (String) -> Unit,
    onToggleTheme: ()-> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp,
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),

                    value = query,
                    onValueChange = { onQueryChange(it) },

                    label = {
                        Text("Search")
                    },

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search icon"
                        )
                    },

                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            searchRecipe()
                        }
                    ),

                    textStyle = TextStyle(MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    )

                )

                ConstraintLayout(modifier = Modifier.align(Alignment.CenterVertically)) {
                    val (menu) = createRefs()

                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier
                            .constrainAs(menu){
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        ) {
                        Icon(
                            imageVector = Icons.Rounded.DarkMode,
                            contentDescription = "Theme Toggle Button",
                            )
                    }
                }


            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)) {
                items(items = getAllFoodCategory(), itemContent = { item ->
                    FoodCategoryChip(
                        category = item.value,
                        isSelected = selectedCategory?.value == item.value,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                        },
                        onExecuteSearch = {
                            searchRecipe()
                        }
                    )
                })
            }
        }
    }
}