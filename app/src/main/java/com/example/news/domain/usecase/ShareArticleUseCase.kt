package com.example.news.domain.usecase

import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepository
import javax.inject.Inject

class ShareArticleUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.shareArticle(article)
    }
}