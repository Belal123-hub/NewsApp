package com.example.news.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.news.data.local.NewsDatabase
import com.example.news.data.mapper.toDomain
import com.example.news.data.mapper.toEntity
import com.example.news.data.remote.NewsApiService
import com.example.news.data.remote.NewsRemoteMediator
import com.example.news.data.util.ShareUtils
import com.example.news.domain.model.Article
import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDatabase: NewsDatabase,
    private val shareUtils: ShareUtils
) : NewsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlines(country: String, query: String?): Flow<PagingData<Article>> {
        return if (query.isNullOrEmpty()) {
            // Original paging with remote mediator
            Pager(
                config = PagingConfig(pageSize = 20),
                remoteMediator = NewsRemoteMediator(newsApiService, newsDatabase, country),
                pagingSourceFactory = { newsDatabase.articleDao().pagingSource() }
            ).flow.map { pagingData ->
                pagingData.map { articleEntity ->
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
        } else {
            // Search from local database
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { newsDatabase.articleDao().searchPagingSource(query) }
            ).flow.map { pagingData ->
                pagingData.map { articleEntity ->
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

    override suspend fun saveToHistory(article: HistoryArticles) {
        newsDatabase.historyDao().insert(article.toEntity())
    }

    override fun getHistory(): Flow<PagingData<HistoryArticles>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {newsDatabase.historyDao().getAllHistory()}
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun shareArticle(article: Article) {
        shareUtils.shareNews(article)
    }
}