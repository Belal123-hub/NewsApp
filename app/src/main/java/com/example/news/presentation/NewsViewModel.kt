package com.example.news.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.remote.NewsApiServiceImpl
import com.example.news.data.remote.createKtorClient
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val newsApiService = NewsApiServiceImpl(createKtorClient())

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val newsResponse = newsApiService.getTopHeadlines(page = 4, pageSize = 4)
                Log.d("NewsViewModel", "Fetched news: ${newsResponse.articles.size} articles")
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Failed to fetch news: ${e.message}", e)
            }
        }
    }
}