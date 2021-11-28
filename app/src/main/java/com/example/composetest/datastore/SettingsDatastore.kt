package com.example.composetest.datastore

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.composetest.presentation.BaseApplication
import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Singleton
class SettingsDatastore @Inject constructor(
    val app: BaseApplication,
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name ="settings ")
    val isDarkTheme = mutableStateOf(false)

    private val scope = MainScope()

    init {
        observeDataStore()
    }

    fun toggleTheme()
    {
        scope.launch {
            app.dataStore.edit { preferences ->
                val current = preferences[THEME_KEY]?:false
                preferences[THEME_KEY] = !current
            }
        }
    }

    private fun observeDataStore()
    {
        app.dataStore.data.onEach { preferences ->
            isDarkTheme.value = preferences[THEME_KEY]?:false
        }.launchIn(scope)
    }

    companion object{
        private val THEME_KEY = booleanPreferencesKey("dark_theme_key")
    }
}