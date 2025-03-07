package com.example.news.presentation.newsList


import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier


@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    padding: PaddingValues
) {
    val news by viewModel.news.collectAsState(initial = emptyList())
    Log.d("NewsListScreen", "Number of articles: ${news.size}") // Log the number of articles

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        items(news) { article ->
            NewsItem(article = article)
        }
    }
}