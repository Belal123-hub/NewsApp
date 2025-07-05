// New domain-level interactor to centralize business logic
package com.example.news.domain

import com.example.news.domain.model.Article
import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.usecase.SaveToHistoryUseCase
import com.example.news.domain.usecase.ShareArticleUseCase
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    private val saveToHistoryUseCase: SaveToHistoryUseCase,
    private val shareArticleUseCase: ShareArticleUseCase
) {
    suspend fun saveToHistory(article: Article) {
        saveToHistoryUseCase(
            HistoryArticles(
                url = article.url,
                title = article.title,
                description = article.description,
                urlToImage = article.imageUrl,
                publishedAt = article.publishedAt,
                source = article.source,
                accessedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun shareArticle(article: Article) {
        shareArticleUseCase(article)
    }
}
