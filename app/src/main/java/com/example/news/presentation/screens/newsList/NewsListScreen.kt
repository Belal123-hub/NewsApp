package com.example.news.presentation.screens.newsList

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.domain.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    navController: NavController,
    news: LazyPagingItems<Article>,
    padding: PaddingValues,
    viewModel: NewsListViewModel
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        viewModel.searchNews(searchQuery)
    }
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

    Column (modifier = Modifier.fillMaxSize()){
        SearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                viewModel.searchNews(it)
            },
            onSearch = {
                active = false
                viewModel.searchNews(searchQuery)
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Search news...") }
        ) {
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
        ) {
            if (news.loadState.refresh is LoadState.Loading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
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

}