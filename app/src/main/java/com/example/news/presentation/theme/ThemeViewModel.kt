package com.example.news.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.preferences.AppPreferences
import com.example.news.domain.model.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val themeFlow = preferences.themeModeFlow

    val uiState: StateFlow<ThemeUiState> = themeFlow
        .map { mode ->
            when (mode) {
                ThemeMode.DARK -> ThemeUiState.Dark
                ThemeMode.LIGHT -> ThemeUiState.Light
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = when (preferences.loadTheme()) {
                ThemeMode.DARK -> ThemeUiState.Dark
                ThemeMode.LIGHT -> ThemeUiState.Light
            }
        )

    fun toggleTheme() {
        viewModelScope.launch {
            val newMode = when (uiState.value) {
                is ThemeUiState.Dark -> ThemeMode.LIGHT
                is ThemeUiState.Light -> ThemeMode.DARK
            }
            preferences.saveTheme(newMode)
        }
    }
}
