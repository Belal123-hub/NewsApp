package com.example.news.presentation.theme

import com.example.news.data.preferences.AppPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeViewModelTest {

    private lateinit var appPreferences: AppPreferences

    @Before
    fun setUp() {
        appPreferences = mockk(relaxed = true)
    }

    // Tests that toggling theme from Light -> Dark
    @Test
    fun toggleTheme_shouldUpdateStateToDark_andSavePreference() = runTest {
        // Given current theme is Light
        every { appPreferences.loadTheme() } returns false
        every { appPreferences.isDarkTheme } returns flowOf(false)

        val viewModel = ThemeViewModel(appPreferences)

        // When
        viewModel.toggleTheme()

        // Then
        assertTrue(viewModel.uiState.value is ThemeUiState.Dark)
        verify { appPreferences.saveTheme(true) }
    }

    // Tests that toggling theme from Dark -> Light
    @Test
    fun toggleTheme_shouldUpdateStateToLight_andSavePreference() = runTest {
        // Given current theme is Dark
        every { appPreferences.loadTheme() } returns true
        every { appPreferences.isDarkTheme } returns flowOf(true)

        val viewModel = ThemeViewModel(appPreferences)

        // When
        viewModel.toggleTheme()

        // Then
        assertTrue(viewModel.uiState.value is ThemeUiState.Light)
        verify { appPreferences.saveTheme(false) }
    }
}
