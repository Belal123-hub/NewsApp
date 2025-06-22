package com.example.news.presentation.screens.history

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.NavController
import com.example.news.domain.model.HistoryArticles
import com.example.news.navigation.AppScreens
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import java.util.*

class HistoryItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()
// Tests that HistoryItem shows article info and navigates to WebView on click
    @Test
    fun historyItem_displaysData_and_navigatesOnClick() {
        val fakeItem = HistoryArticles(
            title = "Test Title",
            url = "https://example.com",
            urlToImage = null,
            source = "Example Source",
            accessedAt = Date().time,
            description = "Test description",
            publishedAt = "2023-06-22T10:00:00Z"
        )

        val mockNavController = mockk<NavController>(relaxed = true)

        composeTestRule.setContent {
            HistoryItem(
                item = fakeItem,
                navController = mockNavController
            )
        }

        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Source: Example Source").assertIsDisplayed()
        composeTestRule.onNodeWithText("Source: Example Source").assertIsDisplayed()

        composeTestRule.onNodeWithText("Test Title").performClick()

        val expectedRoute = AppScreens.WebViewScreen.createRoute(java.net.URLEncoder.encode(fakeItem.url, "UTF-8"))
        verify { mockNavController.navigate(expectedRoute) }

    }
}
