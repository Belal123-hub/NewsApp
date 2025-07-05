package com.example.news.presentation.screens.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.data.network.NetworkMonitor
import com.example.news.domain.NewsInteractor
import com.example.news.domain.model.Article
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import com.example.news.util.NetworkStatusObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val newsInteractor: NewsInteractor,
    networkMonitor: NetworkMonitor
) : ViewModel() {
    private val networkObserver = NetworkStatusObserver(networkMonitor, this)
    val isOnline = networkObserver.isOnline
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
            newsInteractor.saveToHistory(article)
        }
    }

    fun shareArticle(article: Article) {
        viewModelScope.launch {
            newsInteractor.shareArticle(article)
        }
    }
}
