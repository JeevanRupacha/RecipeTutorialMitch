package com.example.composetest.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composetest.presentation.components.*
import java.util.*

private val LightColorPalette = lightColors(
        primary = Blue600,
        primaryVariant = Blue400,
        onPrimary = Black2,
        secondary = Color.White,
        secondaryVariant = Teal300,
        onSecondary = Color.Black,
        error = RedErrorDark,
        onError = RedErrorLight,
        background = Grey1,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
)

private val DarkColorPalette = darkColors(
    primary = Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    dialogQueue: Queue<GenericDialogInfo>? = null,
    isNetworkAvailable: Boolean,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    MaterialTheme(
            colors = if(darkTheme) DarkColorPalette else LightColorPalette,
            typography = InterTypography,
            shapes = Shapes,
    ){
        Box(modifier = Modifier.fillMaxSize())
        {
            Column{
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }

            ProcessDialogQueue(dialogQueue = dialogQueue)


        }
    }
}

@Composable
fun ProcessDialogQueue(dialogQueue: Queue<GenericDialogInfo>?)
{
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.descrition,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}