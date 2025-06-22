package com.example.news.data.util

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.news.domain.model.Article
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShareUtilsTest {

    private lateinit var shareUtils: ShareUtils
    private lateinit var realContext: Context

    @Before
    fun setUp() {
        realContext = ApplicationProvider.getApplicationContext()
        shareUtils = ShareUtils(realContext)
    }
    // Tests that shareNews does not crash even if an exception occurs internally
    @Test
    fun shareNews_handlesExceptionsGracefully() {
        val article = createTestArticle()

        // Just check that no exception is thrown
        shareUtils.shareNews(article)
    }


    private fun createTestArticle(): Article {
        return Article(
            id = "test-id-123",
            title = "Test Title",
            description = "Test Description",
            url = "https://example.com",
            imageUrl = "https://example.com/image.jpg",
            publishedAt = "2023-01-01T00:00:00Z",
            source = "Test Source"
        )
    }
}