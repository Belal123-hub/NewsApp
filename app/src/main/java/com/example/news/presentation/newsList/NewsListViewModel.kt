package com.example.news.presentation.newsList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.model.Article
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {
    private val _news = MutableStateFlow<List<Article>>(emptyList())
    val news: StateFlow<List<Article>> get() = _news

    init {
        fetchNews() // Call fetchNews in the ViewModel's init block
    }

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val news = getTopHeadlinesUseCase(country = "us", page = 1, pageSize = 10)
                _news.value = news
                Log.d("NewsListViewModel", "Fetched ${news.size} articles") // Log the number of articles
            } catch (e: Exception) {
                Log.e("NewsListViewModel", "Error fetching news: ${e.message}", e) // Log errors
            }
        }
    }
}
