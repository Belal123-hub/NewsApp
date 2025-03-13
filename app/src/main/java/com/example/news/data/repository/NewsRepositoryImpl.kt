package com.example.news.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.news.data.local.NewsDatabase
import com.example.news.data.model.ApiNewsResponse
import com.example.news.data.remote.NewsApiService
import com.example.news.data.remote.NewsRemoteMediator
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDatabase: NewsDatabase
) : NewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlines(country: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NewsRemoteMediator(newsApiService, newsDatabase, country),
            pagingSourceFactory = { newsDatabase.articleDao().pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { articleEntity ->
                // Map the Room entity to the domain model
                Article(
                    id = articleEntity.url,
                    title = articleEntity.title,
                    description = articleEntity.description,
                    url = articleEntity.url,
                    imageUrl = articleEntity.urlToImage,
                    publishedAt = articleEntity.publishedAt,
                    source = articleEntity.source
                )
            }
        }
    }
}