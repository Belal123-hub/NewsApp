package com.example.news.domain.usecase

import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.repository.NewsRepository
import javax.inject.Inject

class SaveToHistoryUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: HistoryArticles) {
        repository.saveToHistory(article)
    }
}