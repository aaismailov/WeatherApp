package com.example.weatherapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        fontFamily = FontFamily(
            Font(R.font.montserrat_medium)
        ),
        textAlign = TextAlign.Center
    ),
    h4 = TextStyle (
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        fontFamily = FontFamily(
            Font(R.font.montserrat_semibold)
        ),
        textAlign = TextAlign.Center
    ),
    h5 = TextStyle (
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        fontFamily = FontFamily(
            Font(R.font.montserrat_bold)
        ),
        textAlign = TextAlign.Center
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        fontFamily = FontFamily(
            Font(R.font.montserrat_medium)
        ),
        textAlign = TextAlign.Center
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(
            Font(R.font.montserrat_regular)
        ),
        fontSize = 16.sp
    )
)