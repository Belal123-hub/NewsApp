package com.example.news.presentation.screens.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.domain.model.Article
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {
    private var currentQuery = MutableStateFlow<String?>(null)

    val newsPagingData: Flow<PagingData<Article>> = currentQuery
        .flatMapLatest { query ->
            getTopHeadlinesUseCase(country = "us", query = query)
        }
        .cachedIn(viewModelScope)

    fun searchNews(query: String) {
        currentQuery.value = query.ifEmpty { null }
    }
}