// GetTopHeadlinesUseCaseTest.kt
package com.example.news.domain.usecase

import androidx.paging.PagingData
import com.example.news.domain.model.Article
import com.example.news.domain.repository.NewsRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTopHeadlinesUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: GetTopHeadlinesUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetTopHeadlinesUseCase(repository)
    }
// Tests that the use case calls repository with only a country parameter
    @Test
    fun `invoke should call repository getTopHeadlines with country`() = runTest {
        val testCountry = "us"
        val testPagingData = PagingData.empty<Article>()
        every { repository.getTopHeadlines(testCountry, null) } returns flowOf(testPagingData)
        val result = useCase(testCountry)
        coVerify { repository.getTopHeadlines(testCountry, null) }
        assert(result is kotlinx.coroutines.flow.Flow<PagingData<Article>>)
    }
// Tests that the use case calls repository with both country and query
    @Test
    fun `invoke should call repository getTopHeadlines with country and query`() = runTest {
        val testCountry = "us"
        val testQuery = "technology"
        val testPagingData = PagingData.empty<Article>()
        every { repository.getTopHeadlines(testCountry, testQuery) } returns flowOf(testPagingData)
        val result = useCase(testCountry, testQuery)
        coVerify { repository.getTopHeadlines(testCountry, testQuery) }
        assert(result is kotlinx.coroutines.flow.Flow<PagingData<Article>>)
    }

}