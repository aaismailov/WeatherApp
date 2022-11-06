package com.example.weatherapp.ui.components.texts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.theme.Typography

@Composable
fun ScreenSubTitle(
    modifier: Modifier = Modifier,
    text: String
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    val subtext = text.split(" ").joinToString("\r\n")
    Text(
        text = subtext,
        style = Typography.subtitle2,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .align(Alignment.Center),
        maxLines = 2,
        textAlign = TextAlign.Center
    )
}