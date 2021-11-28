package com.example.composetest.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetest.util.TAG

@Composable
fun ConnectivityMonitor(isNetworkAvailable: Boolean) {

    if(!isNetworkAvailable)
    {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "No Internet Connection ",
                style = MaterialTheme.typography.h6
            )
        }
    }
}