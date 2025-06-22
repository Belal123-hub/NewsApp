package com.example.news.presentation.screens.newsList

import com.MainDispatcherRule
import com.example.news.data.network.NetworkMonitor
import com.example.news.domain.model.Article
import com.example.news.domain.usecase.GetTopHeadlinesUseCase
import com.example.news.domain.usecase.SaveToHistoryUseCase
import com.example.news.domain.usecase.ShareArticleUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule() // Handles test dispatcher

    private val getTopHeadlinesUseCase = mockk<GetTopHeadlinesUseCase>(relaxed = true)
    private val saveToHistoryUseCase = mockk<SaveToHistoryUseCase>(relaxed = true)
    private val shareArticleUseCase = mockk<ShareArticleUseCase>(relaxed = true)
    private val networkMonitor = mockk<NetworkMonitor>(relaxed = true)

    private lateinit var viewModel: NewsListViewModel

    @Before
    fun setUp() {
        viewModel = NewsListViewModel(
            getTopHeadlinesUseCase,
            saveToHistoryUseCase,
            shareArticleUseCase,
            networkMonitor
        )
    }
// Verifies that calling saveToHistory triggers the SaveToHistoryUseCase
    @Test
    fun `saveToHistory calls SaveToHistoryUseCase`() = runTest {
        val article = Article(
            url = "url",
            title = "title",
            description = "desc",
            imageUrl = "img",
            publishedAt = "date",
            source = "source",
            id = "id"
        )

        viewModel.saveToHistory(article)

        coVerify {
            saveToHistoryUseCase(
                match {
                    it.url == article.url && it.title == article.title
                }
            )
        }
    }
// Verifies that calling shareArticle triggers the ShareArticleUseCase
    @Test
    fun `shareArticle calls ShareArticleUseCase`() = runTest {
        val article = Article(
            url = "url",
            title = "title",
            description = "desc",
            imageUrl = "img",
            publishedAt = "date",
            source = "source",
            id = "id"
        )

        viewModel.shareArticle(article)

        coVerify { shareArticleUseCase(article) }
    }
}
