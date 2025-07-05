package com.example.news.domain.usecase

import androidx.paging.PagingData
import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<HistoryArticles>> {
        return repository.getHistory()
    }
}