package com.example.weatherapp.ui.components.texts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.weatherapp.ui.theme.Typography

@Composable
fun ScreenTitle(
    modifier: Modifier = Modifier,
    text: String
) = Box(
    modifier = modifier
) {
    Text(
        text = text,
        style = Typography.h4,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .align(Alignment.Center)
    )
}