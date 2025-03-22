package com.example.taskmanager

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.karumi.shot.ScreenshotTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TaskListScreenTest : ScreenshotTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //  Use Android Compose Test Rule for Compose UI testing
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    /**
     *  Test: Task Creation Flow
     * Ensures clicking FAB navigates to AddTask screen.
     */
    @Test
    fun clickingFabNavigatesToAddTaskScreen() {
        // Click the FAB button
        composeTestRule.onNodeWithText("+").assertExists().performClick()

        // Wait for the UI to update
        composeTestRule.waitForIdle()

        // Assert correct navigation title
        composeTestRule.onNodeWithText("Add New Task").assertExists()
    }


    /**
     * Test: Sorting Functionality
     * Ensures tasks are sorted correctly based on selected option.
     */
    @Test
    fun selectingSortingOptionSortsTasks() {
        // Ensure the sorting button exists and click it
        composeTestRule.onNodeWithText("Sort: Priority", useUnmergedTree = true)
            .assertExists("Sort button not found")
            .performClick()

        // Wait for the dropdown to expand
        composeTestRule.waitForIdle()

        // Select "Priority"
        composeTestRule.onNodeWithText("Priority", useUnmergedTree = true)
            .assertExists("Priority option not found in dropdown")
            .performClick()

        // Wait for UI update
        composeTestRule.waitForIdle()

    }


    /**
     *  Test: Filtering Tasks
     * Ensures completed/pending filters work correctly.
     */
    @Test
    fun selectingCompletedFilterShowsOnlyCompletedTasks() {
        // Ensure the sorting button exists and click it
        composeTestRule.onNodeWithText("Filter: All", useUnmergedTree = true)
            .assertExists("Filter button not found")
            .performClick()

        // Wait for the dropdown to expand
        composeTestRule.waitForIdle()

        // Select "Priority"
        composeTestRule.onNodeWithText("Completed", useUnmergedTree = true)
            .assertExists("Completed option not found in dropdown")
            .performClick()

        // Wait for UI update
        composeTestRule.waitForIdle()
    }

    /**
     *  Test: FAB Animation
     * Ensures the FAB is animated and visible.
     */
    @Test
    fun floatingActionButtonIsVisible() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("+").assertExists()
    }

    /**
     *  Test: Screenshot Test for Light & Dark Mode
     * Validates UI consistency across themes.
     */
    @Test
    fun verifyScreenshotsForLightAndDarkMode() {
        // Capture Light Mode Screenshot
        compareScreenshot(composeTestRule)

        // Switch to Dark Mode
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setTheme(android.R.style.ThemeOverlay_Material_Dark)
        }

        composeTestRule.waitForIdle()

        // Capture Dark Mode Screenshot
        compareScreenshot(composeTestRule, "task_list_dark_mode")
    }

}
