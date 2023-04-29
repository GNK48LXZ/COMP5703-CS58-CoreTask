package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@Composable
fun StarRate(rate:Double){

    val rate = rate.toFloat()

    RatingBar(
        value = rate,
        config = RatingBarConfig()
            .style(RatingBarStyle.HighLighted)
            .hideInactiveStars(false),
        onValueChange = {
        },
        onRatingChanged = {
        }
    )
}