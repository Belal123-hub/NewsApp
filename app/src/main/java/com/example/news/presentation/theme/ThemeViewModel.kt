package com.example.news.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val themeFlow = preferences.isDarkTheme

    val uiState: StateFlow<ThemeUiState> = themeFlow
        .map { isDark ->
            if (isDark) ThemeUiState.Dark else ThemeUiState.Light
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = if (preferences.loadTheme()) ThemeUiState.Dark else ThemeUiState.Light
        )

    fun toggleTheme() {
        viewModelScope.launch {
            preferences.saveTheme(
                when (uiState.value) {
                    is ThemeUiState.Dark -> false
                    is ThemeUiState.Light -> true
                }
            )
        }
    }
}
