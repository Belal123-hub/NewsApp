package com.example.news.presentation.screens.components

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import com.example.news.presentation.components.DateFilter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SdkSuppress(minSdkVersion = Build.VERSION_CODES.O)
@RunWith(AndroidJUnit4::class)
class DateFilterTest {

    @get:Rule
    val composeTestRule = createComposeRule()
// Tests that clicking the "Pick Date" button shows the date picker dialog
    @Test
    fun dateFilter_clickPickDate_showsDatePickerDialog() {
        composeTestRule.setContent {
            DateFilter(
                selectedDate = "",
                onDateChanged = {}
            )
        }

        // Use the test tag instead of text
        composeTestRule.onNodeWithTag("pickDateButton").performClick()

        // Wait for dialog to appear
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("OK").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("OK").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
    }
// Tests that clicking "Cancel" on the date picker dialog closes it
    @Test
    fun dateFilter_datePickerCancel_dismissesDialog() {
        composeTestRule.setContent {
            DateFilter(
                selectedDate = "",
                onDateChanged = {}
            )
        }

        composeTestRule.onNodeWithTag("pickDateButton").performClick()
        composeTestRule.onNodeWithText("Cancel").performClick()

        // Wait for dialog to disappear
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("OK").fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onNodeWithText("OK").assertDoesNotExist()
    }
// Verifies that all expected UI elements are shown in the DateFilter component
    @Test
    fun dateFilter_showsAllUIElements() {
        composeTestRule.setContent {
            MaterialTheme {
                DateFilter(
                    selectedDate = "01/01/2025",
                    onDateChanged = {}
                )
            }
        }

        // Now using exact hardcoded strings
        composeTestRule.onNodeWithText("Select Date to Filter News").assertIsDisplayed()
        composeTestRule.onNodeWithText("01/01/2025").assertIsDisplayed()
        composeTestRule.onNodeWithText("Selected Date").assertExists() // For the label
        composeTestRule.onNodeWithText("Pick a Date").assertIsDisplayed()
    }

}
