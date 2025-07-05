package com.example.news.domain.usecase

import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class SaveToHistoryUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    private val _errors = MutableSharedFlow<Throwable>()
    val errors = _errors.asSharedFlow() // Exposed for observing errors in UI or ViewModel

    suspend operator fun invoke(article: HistoryArticles) {
        try {
            repository.saveToHistory(article)
        } catch (e: Exception) {
            _errors.emit(e) // Emit the error for UI to handle (e.g., Snackbar, Toast)
        }
    }
}
