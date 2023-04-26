package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun StarRate(){
    var rating: Float by remember { mutableStateOf(3.5f) }

    RatingBar(
        value = rating,
        config = RatingBarConfig()
            .style(RatingBarStyle.HighLighted)
            .hideInactiveStars(false),
        onValueChange = {
            rating = rating
        },
        onRatingChanged = {
        }
    )
}