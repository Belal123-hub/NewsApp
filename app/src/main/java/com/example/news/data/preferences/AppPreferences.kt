package com.example.news.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AppPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val DARK_THEME_KEY = "dark_theme_enabled"

    // Reactive flow over SharedPreferences
    val isDarkTheme: Flow<Boolean> = callbackFlow {
        // Initial value
        trySend(loadTheme())

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == DARK_THEME_KEY) {
                trySend(loadTheme())
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.distinctUntilChanged()

    fun loadTheme(): Boolean = prefs.getBoolean(DARK_THEME_KEY, false)

    fun saveTheme(isDark: Boolean) {
        prefs.edit().putBoolean(DARK_THEME_KEY, isDark).apply()
    }
}
