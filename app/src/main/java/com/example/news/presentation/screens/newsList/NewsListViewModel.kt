package com.example.news.presentation.screens.newsList

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.data.network.NetworkMonitor
import com.example.news.domain.model.Article
import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import com.example.news.domain.usecase.SaveToHistoryUseCase
import com.example.news.domain.usecase.ShareArticleUseCase
import com.example.news.presentation.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val saveToHistoryUseCase: SaveToHistoryUseCase,
    private val shareArticleUseCase: ShareArticleUseCase,
    networkMonitor:NetworkMonitor
) : BaseViewModel(networkMonitor) {
    private var currentQuery = MutableStateFlow<String?>(null)

    val newsPagingData: Flow<PagingData<Article>> = currentQuery
        .flatMapLatest { query ->
            getTopHeadlinesUseCase(country = "us", query = query)
        }
        .cachedIn(viewModelScope)

    fun searchNews(query: String) {
        currentQuery.value = query.ifEmpty { null }
    }

    fun saveToHistory(article: Article) {
        viewModelScope.launch {
            saveToHistoryUseCase(
                HistoryArticles(
                    url = article.url,
                    title = article.title,
                    description = article.description,
                    urlToImage = article.imageUrl,
                    publishedAt = article.publishedAt,
                    source = article.source,
                    accessedAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun shareArticle(article: Article) {
        viewModelScope.launch {
            shareArticleUseCase(article)
        }
    }
}