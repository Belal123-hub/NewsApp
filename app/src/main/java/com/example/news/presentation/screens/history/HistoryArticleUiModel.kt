package com.example.news.presentation.screens.history

data class HistoryArticleUiModel(
    val title: String,
    val imageUrl: String?,
    val encodedUrl: String,
    val formattedSource: String,
    val formattedDate: String
)