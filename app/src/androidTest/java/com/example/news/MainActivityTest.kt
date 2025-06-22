package com.example.news

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
// Checks that the MainActivity launches and displays expected UI content
    @Test
    fun mainActivity_launches_and_displaysContent() {
        // Replace "News" with any text you expect in your main screen, e.g. app title or a visible label
        composeTestRule.onNodeWithText("News").assertIsDisplayed()
    }
}