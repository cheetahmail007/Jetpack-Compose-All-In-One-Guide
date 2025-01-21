package com.example.jetpack_compose_all_in_one.lessons.lesson_9

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack_compose_all_in_one.R
import com.example.jetpack_compose_all_in_one.ui.components.LessonHeader
import com.example.jetpack_compose_all_in_one.ui.theme.dp_15
import com.example.jetpack_compose_all_in_one.utils.LogicPager

@Preview
@Composable
fun Lesson_9() {
    LessonContent()
}

@Composable
private fun LessonContent() {
    val currentPage = rememberSaveable { mutableIntStateOf(0) }

    LogicPager(
        pageCount = 3,
        currentPage = currentPage
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LessonHeader(
                stringArrayResource(R.array.lesson_9_ui_test)[currentPage.value],
                Modifier
                    .fillMaxWidth()
                    .padding(dp_15),
                TextAlign.Center
            )

            when (currentPage.value) {
                0 -> CounterDisplay()
                1 -> ArticleDemo()
                2 -> LazyColumnTesting()
            }
        }
    }
}


@Preview
@Composable
fun ArticleDemo() {
    val content = """
        This is the article content.
        
        # This is a heading
        
        - Bullet point 1
        - Bullet point 2
        - Bullet point 3
        
        This is some additional content.
    """.trimIndent()

    val onClick: () -> Unit = {
        // Handle click event here
    }

    ArticleContent(
        content = content,
        onClick = onClick
    )
}

@Composable
fun ArticleContent(content: String, onClick: () -> Unit = {}) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (content.isEmpty()) {
            Text(
                text = "No content available",
                style = MaterialTheme.typography.subtitle1
            )
        } else {
            val paragraphs = content.split("\n")
            for (paragraph in paragraphs) {
                val trimmedParagraph = paragraph.trim()
                if (trimmedParagraph.startsWith("# ")) {
                    Text(
                        text = trimmedParagraph.removePrefix("# "),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else if (trimmedParagraph.startsWith("- ")) {
                    Text(
                        text = trimmedParagraph.removePrefix("- "),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                } else {
                    Text(
                        text = trimmedParagraph,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
        Text(
            text = "Click here",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun LazyColumnTesting(){

    val myList = (1..20).toList()

    LazyColumn(modifier = Modifier.testTag("lazyColumn")) {
        items(myList){
            androidx.compose.material3.Text(modifier = Modifier.padding(10.dp), text = it.toString())
        }
    }
}

@Preview
@Composable
fun PreviewArticleContent() {
    ArticleContent(
        content = "This is the article content.\n\n" +
                "# This is a heading\n\n" +
                "- Bullet point 1\n" +
                "- Bullet point 2\n" +
                "- Bullet point 3\n\n" +
                "This is some additional content."
    )
}
