package com.example.news.presentation.theme

import com.example.news.data.preferences.AppPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeViewModelTest {

    private lateinit var appPreferences: AppPreferences
    private lateinit var viewModel: ThemeViewModel

    @Before
    fun setUp() {
        appPreferences = mockk(relaxed = true)
        every { appPreferences.loadTheme() } returns false
        viewModel = ThemeViewModel(appPreferences)
    }
// Tests that toggling theme changes the value to dark and saves preference
    @Test
    fun toggleTheme_shouldUpdateStateAndSavePreference() = runTest {
        viewModel.toggleTheme()
        assertEquals(true, viewModel.isDarkTheme.value)
        verify { appPreferences.saveTheme(true) }
    }
// Tests toggling theme from dark to light and verifies preference saved as false
    @Test
    fun toggleTheme_shouldFlipBooleanBackAndForth() = runTest {
        every { appPreferences.loadTheme() } returns true
        val viewModel = ThemeViewModel(appPreferences)
        viewModel.toggleTheme()
        assertEquals(false, viewModel.isDarkTheme.value)
        verify { appPreferences.saveTheme(false) }
    }
}
