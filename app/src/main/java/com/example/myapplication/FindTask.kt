package com.example.myapplication

import android.annotation.SuppressLint
import android.widget.AdapterView.OnItemClickListener
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
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
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ListItem

@Preview
@Composable
fun FindTask() {
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
                text = "Browse tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = Poppins
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        ListScreen(list)
    }
}

data class ListItem(
    val taskname: String,
    val location: String,
    val date: String,
    val time: String,
    val status: String,
    val bill: String,
    val imageUrl: Int
)

val list = listOf(
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
        "80",
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
        "80",
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
        "80",
        R.drawable.ic_launcher_foreground
    )
)

@Composable
fun ListItem(item: ListItem) {
    Surface(
        //elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clickable {  }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    item.taskname,
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 20.sp,
                        fontFamily = Poppins
                    ),
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    item.location,
                    style = MaterialTheme.typography.body1.copy(fontSize = 15.sp,fontFamily = Poppins),
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    item.date,
                    style = MaterialTheme.typography.body1.copy(fontSize = 15.sp, fontFamily = Poppins),
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    item.time,
                    style = MaterialTheme.typography.body1.copy(fontSize = 15.sp,fontFamily = Poppins),
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    item.status,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 18.sp,
                        fontFamily = Poppins
                    ),
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    "AU " + item.bill + " $",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = MaterialTheme.colors.onSurface
                )
                Image(
                    painter = painterResource(item.imageUrl),
                    contentDescription = "Image",
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Composable
fun ListScreen(list: List<ListItem>) {
    LazyColumn(modifier = Modifier.background(color = Color(0XFFF5F5F5))) {
        items(list.size) { index ->
            ListItem(item = list[index])
        }
    }
}

@Composable
fun DetailScreen(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = Poppins
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

    }
}