package com.example.weatherapp.ui.components.texts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.theme.Typography

@Composable
fun ScreenBody(
    modifier: Modifier = Modifier,
    text: String
) = Box(
    modifier = modifier.padding(vertical = 10.dp)
) {
    Text(
        text = text,
        style = Typography.body1,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .align(Alignment.Center)
    )
}