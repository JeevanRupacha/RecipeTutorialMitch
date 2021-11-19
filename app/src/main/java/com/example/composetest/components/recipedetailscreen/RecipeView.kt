package com.example.composetest.components.recipedetailscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.composetest.R
import com.example.composetest.domain.model.Recipe
import com.example.composetest.util.TAG

@Composable
fun RecipeView(
    recipe: Recipe
){
   Column(
       modifier = Modifier
           .verticalScroll(rememberScrollState()
       )
   ) {
      recipe.featuredImage?.let { url ->
          Log.d(TAG, "RecipeView: $url")
          Image(
              painter = rememberImagePainter(
                  data = url,
                  builder = {
                      placeholder(R.drawable.recipe_default_image)
                      crossfade(true)
                  }
              ),
              contentDescription ="Recipe Image ",
              contentScale = ContentScale.FillWidth,
              modifier = Modifier
                  .requiredHeight(300.dp)
                  .fillMaxWidth(),
              alignment = TopStart
          )
      }

    Column(modifier = Modifier.fillMaxWidth()) {
        recipe.title?.let { title ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h3
                    )

                Text(
                    text = recipe.rating?.let{ it.toString() } ?: "0",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
                        .wrapContentWidth(Alignment.End)
                        .align(CenterVertically),
                    style = MaterialTheme.typography.h5,
                    )

            }
        }
    }

       recipe.publisher?.let { publisher ->
           recipe.dateUpdated?.let{ updated ->
               Text(
                   text = "Updated at ${updated} by $publisher",
                   modifier = Modifier.padding(bottom = 12.dp, start = 8.dp, end= 8.dp),
                   style = MaterialTheme.typography.caption
               )
           }
       }

       for(ingredient in recipe.ingredients){
           Text(
               text = ingredient,
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(bottom = 4.dp, start = 8.dp, end = 8.dp),
               style = MaterialTheme.typography.body2
               )
       }

   }
}