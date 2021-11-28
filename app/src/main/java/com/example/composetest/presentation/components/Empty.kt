package com.example.composetest.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composetest.R

@Composable
fun Empty(){
    Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.undraw_empty_re),
            contentDescription = "Empty Image vector",
            contentScale = ContentScale.Crop
        )
    }
}