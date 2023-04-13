package com.example.jetpack_compose_all_in_one

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpack_compose_all_in_one.ui.theme.dp_3

@Composable
fun SimpleButton() {
    Button(onClick = { }) {
        Text(text = "Simple Button")
    }
}

@Composable
fun ButtonWithBorder() {
    Button(
        onClick = { },
        border = BorderStroke(dp_3, Color.Black)
    ) {
        Text(text = "Simple button with border")
    }
}

@Composable
fun ButtonWithRoundedCorners() {
    Button(
        onClick = { },
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = "Button with rounded corners")
    }
}

@Composable
fun OutlinedButton() {
    OutlinedButton(onClick = { }) {
        Text(text = "Outlined Button")
    }
}

@Composable
fun TextButton() {
    TextButton(onClick = { }) {
        Text(text = "Text Button")
    }
}