package com.example.composetest.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged : (String) -> Unit,
    onExecuteSearch: () -> Unit,
    )
{
   Surface(
       modifier = Modifier.padding(end = 8.dp),
       elevation = 8.dp,
       color = if(isSelected) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.primary,
       shape = CircleShape
   )
   {
       Box(
           modifier = Modifier
               .toggleable(
                   value = isSelected,
                   onValueChange = {
                       onSelectedCategoryChanged(category)
                       onExecuteSearch()
                   }
               )
       ){
           Text(
               text = category,
               style = MaterialTheme.typography.body2,
               color = Color.White,
               modifier = Modifier.padding(8.dp)
           )
       }
   }
}