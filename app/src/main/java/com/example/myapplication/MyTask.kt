package com.example.myapplication

import android.widget.ToggleButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

//navController: NavController

@Composable
fun MyTask(navController: NavController) {
    val pageState = remember { mutableStateOf(1) }

    if (pageState.value == 1) {
        ShowPostTask(pageState,navController,)
    } else if (pageState.value == 2) {
        ShowGetTask(pageState,navController)
    }
}

//pageState: MutableState<Int>, navController: NavController
@Composable
fun ShowPostTask(pageState: MutableState<Int>, navController: NavController) {
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
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(38.dp)
                .background(Color.LightGray, RoundedCornerShape(25.dp))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clickable { pageState.value = 1 }
                        .background(Color.Gray, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Posted Task",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clickable { pageState.value = 2 },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Assign Task",
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
        TaskPostListScreen("Task", navController)
    }
}

@Composable
fun ShowGetTask(pageState: MutableState<Int>, navController: NavController) {
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
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(38.dp)
                .background(Color.LightGray, RoundedCornerShape(25.dp))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clickable { pageState.value = 1 }
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Posted Task",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clickable { pageState.value = 2 }
                        .background(Color.Gray, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Assign Task",
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
        TaskGetListScreen("Task", navController)
    }
}

@Composable
fun TaskPostListScreen(collectionPath: String, navController: NavController) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadMyPostDataFromFirestore(db, collectionPath)
    }

    MyTaskListLazyColumn(taskItems, navController)
}

@Composable
fun TaskGetListScreen(collectionPath: String, navController: NavController) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadMyGetDataFromFirestore(db, collectionPath)
    }

    MyTaskListLazyColumn(taskItems, navController)
}

suspend fun loadMyPostDataFromFirestore(
    db: FirebaseFirestore,
    collectionPath: String
): List<TaskItem> {
    val postTaskList = mutableListOf<TaskItem>()
    val querySnapshot = db.collection(collectionPath).get().await()
    querySnapshot.documents.forEach { document ->
        val taskId = document.id
        val taskTopic = document.getString("taskTopic") ?: ""
        val address = document.getString("address") ?: ""
        val date = document.getString("date") ?: ""
        val startTime = document.getString("startTime") ?: ""
        val endTime = document.getString("endTime") ?: ""
        val status = document.getString("status") ?: ""
        val money = document.getString("money") ?: ""
        val imageUrl = R.drawable.ic_launcher_foreground
        if (user == document.getString("UserID")) {
            postTaskList.add(
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
    }
    return postTaskList
}

suspend fun loadMyGetDataFromFirestore(
    db: FirebaseFirestore,
    collectionPath: String
): List<TaskItem> {
    val getTaskList = mutableListOf<TaskItem>()
    val querySnapshot = db.collection(collectionPath).get().await()
    querySnapshot.documents.forEach { document ->
        val taskId = document.id
        val taskTopic = document.getString("taskTopic") ?: ""
        val address = document.getString("address") ?: ""
        val date = document.getString("date") ?: ""
        val startTime = document.getString("startTime") ?: ""
        val endTime = document.getString("endTime") ?: ""
        val status = document.getString("status") ?: ""
        val money = document.getString("money") ?: ""
        val imageUrl = R.drawable.ic_launcher_foreground
        if (user == document.getString("AssignID")) {
            getTaskList.add(
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
    }
    return getTaskList
}

@Composable
fun MyTaskListLazyColumn(taskItem: List<TaskItem>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .background(color = Color(0XFFF5F5F5))
            .fillMaxWidth()
            .fillMaxHeight()
        //.padding(top = 70.dp)
    ) {
        items(taskItem) { taskItem ->
            Surface(
                //elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(162.dp)
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
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .background(color = background)
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