package com.example.news.domain.repository

import androidx.paging.PagingData
import com.example.news.domain.model.Article
import com.example.news.domain.model.HistoryArticles
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(
        country: String, query: String? = null
    ): Flow<PagingData<Article>>

    suspend fun saveToHistory(article: HistoryArticles)
    fun getHistory(): Flow<PagingData<HistoryArticles>>
    suspend fun shareArticle(article: Article)
    //suspend fun getNewsByDate(date: Date?): List<Article>

}