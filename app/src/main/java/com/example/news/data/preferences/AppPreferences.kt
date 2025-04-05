package com.example.news.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveTheme(isDark: Boolean) {
        prefs.edit { putBoolean("dark_theme", isDark) }
    }

    fun loadTheme(): Boolean {
        return prefs.getBoolean("dark_theme", false) // default = light
    }
}