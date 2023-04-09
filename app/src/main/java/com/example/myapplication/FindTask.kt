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
fun FindTask() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable {/* */ }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Browse tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        ListScreen(list1)
    }
}

data class ListItem(val taskname: String, val location: String,val date: String,val time: String,val bill:String, val imageUrl: Int)

val list1 = listOf(
    ListItem("Task name", "Location","Date(4th April, Tuesday)","Time","80", R.drawable.ic_launcher_foreground),
    ListItem("Task name", "Location","Date(4th April, Tuesday)","Time","80", R.drawable.ic_launcher_foreground),
    ListItem("Task name", "Location","Date(4th April, Tuesday)","Time","80", R.drawable.ic_launcher_foreground),
    ListItem("Task name", "Location","Date(4th April, Tuesday)","Time","80", R.drawable.ic_launcher_foreground),
    ListItem("Task name", "Location","Date(4th April, Tuesday)","Time","80", R.drawable.ic_launcher_foreground),
    ListItem("Task name", "Location","Date(4th April, Tuesday)","Time","80", R.drawable.ic_launcher_foreground)
)

@Composable
fun ListItem(item: ListItem) {
    Surface(
        //elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(158.dp)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.taskname,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    item.location,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    item.date,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    item.time,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
            }
            Column{
                Text(
                    item.bill+"$",
                    style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
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
    LazyColumn {
        items(list.size) { index ->
            ListItem(item = list[index])
        }
    }
}

