package com.example.news.presentation.newsList

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    padding: PaddingValues
) {
    // Collect the PagingData as LazyPagingItems
    val newsPagingData = viewModel.newsPagingData.collectAsLazyPagingItems()
    Log.d("NewsListScreen", "Collected LazyPagingItems with ${newsPagingData.itemCount} items")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        // Use LazyPagingItems directly
        items(newsPagingData.itemCount) { index ->
            val article = newsPagingData[index]
            if (article != null) {
                Log.d("NewsListScreen", "Displaying article: ${article.title}")
                NewsItem(article = article)
            }
        }

        // Show a loading indicator at the bottom when loading the next page
        if (newsPagingData.loadState.append is LoadState.Loading) {
            Log.d("NewsListScreen", "Loading next page...")
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}