// ShareArticleUseCaseTest.kt
package com.example.news.domain.usecase

import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ShareArticleUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: ShareArticleUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ShareArticleUseCase(repository)
    }
// Tests that the ShareArticleUseCase properly calls the repository's method
    @Test
    fun `invoke should call repository shareArticle`() = runTest {
        val article = Article(
            id = "test-id-123",
            title = "Test Title",
            description = "Test Description",
            url = "https://example.com",
            imageUrl = "https://example.com/image.jpg",
            publishedAt = "2023-01-01T00:00:00Z",
            source = "Test Source"
        )
        useCase(article)
        coVerify { repository.shareArticle(article) }
    }
}