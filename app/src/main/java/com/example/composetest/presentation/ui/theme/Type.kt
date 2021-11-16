package com.example.composetest.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.composetest.R

private val Inter = FontFamily(
        Font(R.font.inter_black, FontWeight.Black),
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_extrabold, FontWeight.ExtraBold),
        Font(R.font.inter_extralight, FontWeight.ExtraLight),
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_meduim, FontWeight.Medium),
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_thin, FontWeight.Thin),
)


val InterTypography = Typography(
        h1 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W500,
                fontSize = 30.sp,
        ),
        h2 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp,
        ),
        h3 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
        ),
        h4 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 18.sp,
        ),
        h5 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
        ),
        h6 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
        ),
        subtitle1 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
        ),
        subtitle2 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
        ),
        body1 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        body2 = TextStyle(
                fontFamily = Inter,
                fontSize = 14.sp
        ),
        button = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                color = White
        ),
        caption = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
        ),
        overline = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
        )
)