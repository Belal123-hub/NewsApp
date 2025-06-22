package com.example.news.presentation.theme

import androidx.lifecycle.ViewModel
import com.example.news.data.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(preferences.loadTheme())
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun toggleTheme() {
        val newTheme = !_isDarkTheme.value
        _isDarkTheme.value = newTheme
        preferences.saveTheme(newTheme)
    }
}