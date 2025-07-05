// com.example.news.presentation.screens.history.HistoryArticleUiMapper.kt
package com.example.news.presentation.screens.history

import android.net.Uri
import com.example.news.domain.model.HistoryArticles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun HistoryArticles.toUiModel(): HistoryArticleUiModel {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return HistoryArticleUiModel(
        title = title,
        imageUrl = urlToImage,
        encodedUrl = Uri.encode(url),
        formattedSource = "Source: $source",
        formattedDate = "Accessed: ${dateFormat.format(Date(accessedAt))}"
    )
}
