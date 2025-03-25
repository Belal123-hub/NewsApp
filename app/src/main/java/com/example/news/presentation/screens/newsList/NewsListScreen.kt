package com.example.news.presentation.screens.newsList

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.domain.model.Article

@Composable
fun NewsListScreen(
    navController: NavController,
    news: LazyPagingItems<Article>,
    padding: PaddingValues
) {
    val context = LocalContext.current

    // show toast if there is an error durring refresh
    LaunchedEffect(key1 = news.loadState) {
        if (news.loadState.refresh is LoadState.Error){
            Toast.makeText(
                context,
                "Error: " + (news.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
                ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (news.loadState.refresh is LoadState.Loading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(news.itemCount) { index ->
                    val article = news[index]
                    if (article != null) {
                        Log.d("NewsListScreen", "Displaying article: ${article.title}")
                        NewsItem(
                            article = article,
                            navController = navController
                            )
                    }
                }
                // Show a loading indicator at the bottom when loading the next page
                if (news.loadState.append is LoadState.Loading) {
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
    }
}