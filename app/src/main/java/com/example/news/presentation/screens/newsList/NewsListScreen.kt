package com.example.news.presentation.screens.newsList

import NetworkStatus
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.R
import com.example.news.domain.model.Article
import com.example.news.navigation.AppScreens
import com.example.news.presentation.theme.ThemeUiState
import com.example.news.presentation.theme.ThemeViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    navController: NavController,
    newsFlow: Flow<PagingData<Article>>,
    padding: PaddingValues,
    viewModel: NewsListViewModel,
    themeViewModel: ThemeViewModel
) {
    val news = newsFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val isOnline by viewModel.isOnline.collectAsState()
    val listState = rememberLazyListState()
    val themeUiState by themeViewModel.uiState.collectAsState()
    val isDarkTheme = when (themeUiState) {
        is ThemeUiState.Dark -> true
        is ThemeUiState.Light -> false
    }

    LaunchedEffect(searchQuery) {
        viewModel.searchNews(searchQuery)
    }

    LaunchedEffect(key1 = news.loadState) {
        if (news.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (news.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBarSection(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onToggleTheme = { themeViewModel.toggleTheme() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NetworkStatus(isOnline = isOnline)

            SearchBarSection(
                searchQuery = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    viewModel.searchNews(it)
                },
                onSearch = {
                    active = false
                    viewModel.searchNews(searchQuery)
                },
                active = active,
                onActiveChange = { active = it }
            )

            NewsListContent(
                news = news,
                listState = listState,
                onArticleClick = { article ->
                    viewModel.saveToHistory(article)
                    val encodedUrl = Uri.encode(article.url)
                    navController.navigate(AppScreens.WebViewScreen.createRoute(encodedUrl))
                },
                onShareClick = { article ->
                    viewModel.shareArticle(article)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSection(
    navController: NavController,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.news)) },
        actions = {
            IconButton(onClick = { navController.navigate(AppScreens.HistoryScreen.route) }) {
                Icon(Icons.Default.History, contentDescription = stringResource(R.string.history))
            }
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = stringResource(R.string.toggle_theme)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSection(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit
) {
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchQuery,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        placeholder = { Text(stringResource(R.string.search_news)) },
        leadingIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Search, contentDescription = null)
            }
        }
    ) {}
}

@Composable
fun NewsListContent(
    news: LazyPagingItems<Article>,
    listState: androidx.compose.foundation.lazy.LazyListState,
    onArticleClick: (Article) -> Unit,
    onShareClick: (Article) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp)) {
        if (news.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(news.itemCount) { index ->
                    val article = news[index]
                    if (article != null) {
                        NewsItem(
                            article = article,
                            onClick = { onArticleClick(article) },
                            onShare = { onShareClick(article) }
                        )
                    }
                }
                if (news.loadState.append is LoadState.Loading) {
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



