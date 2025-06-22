package com.example.news.presentation.theme

sealed class ThemeUiState {
    object Light : ThemeUiState()
    object Dark : ThemeUiState()
}