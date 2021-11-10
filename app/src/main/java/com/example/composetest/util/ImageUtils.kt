package com.example.composetest.util

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

//@ExperimentalComposeApi
//@Composable
//fun ImageLoader(
//    url: String, @DrawableRes defaultImage: Int
//): MutableState<Bitmap?> {
//   val bitmapState: MutableState<Bitmap?> =   remember {mutableStateOf(null)}
//
//    Glide.with(AmbientContext.current)
//        .asBitmap()
//        .load(defaultImage)
//        .into(object: CustomTarget<Bitmap>(){
//
//        })
//
//}