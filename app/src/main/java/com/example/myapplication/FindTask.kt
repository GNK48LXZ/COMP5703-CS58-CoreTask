@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ListItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun FindTask(navController: NavController) {
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

        TaskListScreen("Task", navController)
    }
}

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

data class ListItem(
    val taskname: String,
    val location: String,
    val date: String,
    val time: String,
    val status: String,
    val bill: String,
    val imageUrl: Int
)

data class TaskItem(
    val taskId: String,
    val taskName: String,
    val location: String,
    val date: String,
    val time: String,
    val status: String,
    val bill: String,
    val imageUrl: Int
)

@Composable
fun TaskListScreen(collectionPath: String, navController: NavController) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadDataFromFirestore(db, collectionPath)
    }

    TaskListLazyColumn(taskItems, navController)
}

public suspend fun loadDataFromFirestore(
    db: FirebaseFirestore,
    collectionPath: String
): List<TaskItem> {
    val taskList = mutableListOf<TaskItem>()
    val querySnapshot = db.collection(collectionPath).get().await()
    querySnapshot.documents.forEach { document ->
        val taskId = document.id
        Log.d(TAG, "TaskListScreen: $taskId")
        val taskTopic = document.getString("taskTopic") ?: ""
        val address = document.getString("address") ?: ""
        val date = document.getString("date") ?: ""
        val startTime = document.getString("startTime") ?: ""
        val endTime = document.getString("endTime") ?: ""
        val status = document.getString("status") ?: ""
        val money = document.getString("money") ?: ""
        val imageUrl = R.drawable.ic_launcher_foreground
        taskList.add(
            TaskItem(
                taskId = taskId,
                taskName = taskTopic,
                location = address,
                date = date,
                time = "$startTime - $endTime",
                status = status,
                bill = money,
                imageUrl = imageUrl
            )
        )
    }
    return taskList
}


@Composable
fun TaskListLazyColumn(taskItem: List<TaskItem>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .background(color = Color(0XFFF5F5F5))
            .fillMaxWidth()
            .fillMaxHeight()
            //.padding(bottom = 30.dp)
    ) {
        items(taskItem) { taskItem ->
            Surface(
                //elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .clickable { navController.navigate("monitoringDetails/${taskItem.taskId}") }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(color = background)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(color = background)
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text(
                            taskItem.taskName,
                            style = MaterialTheme.typography.h2.copy(
                                fontSize = 20.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.location,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 15.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.date,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 15.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.time,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 15.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.status,
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
                        modifier = Modifier.padding(horizontal = 20.dp).background(color = background)
                    ) {
                        Text(
                            "AU " + taskItem.bill + " $",
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Image(
                            painter = painterResource(taskItem.imageUrl),
                            contentDescription = "Image",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
            }
        }
    }
}