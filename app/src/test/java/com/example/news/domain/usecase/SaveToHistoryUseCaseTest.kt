// SaveToHistoryUseCaseTest.kt
package com.example.news.domain.usecase

import com.example.news.domain.model.HistoryArticles
import com.example.news.domain.repository.NewsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveToHistoryUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: SaveToHistoryUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SaveToHistoryUseCase(repository)
    }
// Tests that SaveToHistoryUseCase correctly delegates to repository method
    @Test
    fun `invoke should call repository saveToHistory`() = runTest {
        val article = HistoryArticles(
            url = "https://news.com",
            title = "Test Title",
            description = "Test Description",
            urlToImage = "https://image.com/image.jpg",
            publishedAt = "2024-01-01T12:00:00Z",
            source = "News Source",
            accessedAt = 1234567890L
        )
        useCase(article)
        coVerify { repository.saveToHistory(article) }
    }
}
