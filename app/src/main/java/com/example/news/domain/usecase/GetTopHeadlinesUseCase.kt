package com.example.news.domain.usecase

import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepository

class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        country: String = "us",
        page: Int,
        pageSize: Int
    ):List<Article>{
        return newsRepository.getTopHeadLines(country,page,pageSize)
    }
}