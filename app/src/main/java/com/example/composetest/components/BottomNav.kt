package com.example.composetest.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun BottomNav(
    onSearch: ()-> Unit,
    onHome: () -> Unit,
    onMoreVert: () -> Unit,
)
{
    BottomNavigation(
        elevation = 15.dp,
        modifier = Modifier.zIndex(5f)
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "search icon", tint = Color.White) },
            selected = false,
            onClick = onSearch
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "search icon", tint = Color.White) },
            selected = true,
            onClick = onHome
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "search icon", tint = Color.White) },
            selected = false,
            onClick = onMoreVert
        )
    }
}