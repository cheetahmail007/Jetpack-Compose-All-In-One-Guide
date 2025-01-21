package com.example.jetpack_compose_all_in_one.lessons.lesson_9

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jetpack_compose_all_in_one.ui.views.main_ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LazyColumnTestingExample {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeRule.activity.setContent {
            LazyColumnTesting()
        }
    }

    @Test
    fun testAllItemsVisible(){
        composeRule.apply {
            onNodeWithText("1").assertIsDisplayed()
            onNodeWithTag("lazyColumn").performScrollToNode(hasText("20"))
            onNodeWithText("19").assertIsDisplayed()
        }
    }
}