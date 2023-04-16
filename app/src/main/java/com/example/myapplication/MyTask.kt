package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun MyTask() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .height(720.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "My tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = Poppins
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        //ListScreen(myList)
    }
}

val myList = listOf(
    ListItem(
        "Task name",
        "Location",
        "Date(4th April, Tuesday)",
        "Time",
        "Open",
        "40",
        R.drawable.ic_launcher_foreground
    ),
    ListItem(
        "Task name",
        "Location",
        "Date(4th April, Tuesday)",
        "Time",
        "Open",
        "200",
        R.drawable.ic_launcher_foreground
    ),
    ListItem(
        "Task name",
        "Location",
        "Date(4th April, Tuesday)",
        "Time",
        "Open",
        "70",
        R.drawable.ic_launcher_foreground
    ),
    ListItem(
        "Task name",
        "Location",
        "Date(4th April, Tuesday)",
        "Time",
        "Open",
        "40",
        R.drawable.ic_launcher_foreground
    ),
    ListItem(
        "Task name",
        "Location",
        "Date(4th April, Tuesday)",
        "Time",
        "Open",
        "80",
        R.drawable.ic_launcher_foreground
    ),
    ListItem(
        "Task name",
        "Location",
        "Date(4th April, Tuesday)",
        "Time",
        "Open",
        "300",
        R.drawable.ic_launcher_foreground
    )
)

