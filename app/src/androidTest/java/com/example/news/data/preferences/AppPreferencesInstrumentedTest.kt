package com.example.news.data.preferences

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppPreferencesInstrumentedTest {

    private lateinit var context: Context
    private lateinit var preferences: AppPreferences

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()

        // Clear previous preferences to ensure test isolation
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit().clear().commit()

        preferences = AppPreferences(context)
    }

    // Tests saving and loading dark mode theme preference returns true
    @Test
    fun saveTheme_and_loadTheme_returnsCorrectValue_dark() {
        preferences.saveTheme(true)
        val isDark = preferences.loadTheme()
        assertThat(isDark).isTrue()
    }
// Tests saving and loading light mode theme preference returns false
    @Test
    fun saveTheme_and_loadTheme_returnsCorrectValue_light() {
        preferences.saveTheme(false)
        val isDark = preferences.loadTheme()
        assertThat(isDark).isFalse()
    }
// Tests that loading theme without saving returns the default value (false)
    @Test
    fun loadTheme_returnsDefaultValue_whenNotSet() {
        val isDark = preferences.loadTheme()
        assertThat(isDark).isFalse() // default should be false
    }
}