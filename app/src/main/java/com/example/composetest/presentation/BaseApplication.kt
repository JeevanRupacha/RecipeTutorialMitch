package com.example.composetest.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.composetest.util.TAG
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    // should be saved in data store
    val isDark = mutableStateOf(false)

    fun toggleLightTheme(){
        isDark.value = !isDark.value
    }
}