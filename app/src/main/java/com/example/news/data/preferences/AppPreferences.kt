// data/preferences/AppPreferences.kt
package com.example.news.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.news.domain.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AppPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val THEME_KEY = "theme_mode"

    val themeModeFlow: Flow<ThemeMode> = callbackFlow {
        trySend(loadTheme())

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == THEME_KEY) {
                trySend(loadTheme())
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.distinctUntilChanged()

    fun saveTheme(themeMode: ThemeMode) {
        prefs.edit().putString(THEME_KEY, themeMode.name).apply()
    }

    fun loadTheme(): ThemeMode {
        val name = prefs.getString(THEME_KEY, ThemeMode.LIGHT.name)
        return try {
            ThemeMode.valueOf(name ?: ThemeMode.LIGHT.name)
        } catch (e: IllegalArgumentException) {
            ThemeMode.LIGHT
        }
    }
}
