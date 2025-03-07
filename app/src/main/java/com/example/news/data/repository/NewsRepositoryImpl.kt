package com.example.news.data.repository

import com.example.news.data.model.ApiNewsResponse
import com.example.news.data.remote.NewsApiService
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val newsApiService: NewsApiService
):NewsRepository {
    override suspend fun getTopHeadLines(
        country: String,
        page: Int,
        pageSize: Int
    ): List<Article> {
        val response:ApiNewsResponse = newsApiService.getTopHeadlines(country,page,pageSize)
        return response.articles.map { apiArticle ->  
            Article(
                id = apiArticle.url,
                title = apiArticle.title,
                description = apiArticle.description,
                url = apiArticle.url,
                imageUrl = apiArticle.urlToImage,
                publishedAt = apiArticle.publishedAt,
                source = apiArticle.source.name
            )
        }

    }

}