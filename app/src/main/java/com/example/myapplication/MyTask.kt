package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ToggleButton
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

//navController: NavController

@Composable
fun MyTask(navController: NavController) {
    val pageState = remember { mutableStateOf(1) }

    if (pageState.value == 1) {
        ShowPostTask(pageState, navController)
    } else if (pageState.value == 2) {
        ShowGetTask(pageState, navController)
    }
}

//pageState: MutableState<Int>, navController: NavController
@Composable
fun ShowPostTask(pageState: MutableState<Int>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .height(700.dp)
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
                        .background(if (pageState.value == 1) buttonColor else Color.Transparent, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Posted Task",
                        fontSize = 12.sp,
                        color = if (pageState.value == 1) Color.Black else MaterialTheme.colors.onSurface
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clickable { pageState.value = 2 }
                        .background(if (pageState.value == 2) buttonColor else Color.Transparent, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Assign Task",
                        fontSize = 12.sp,
                        color = if (pageState.value == 2) Color.Black else MaterialTheme.colors.onSurface
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
                        .background(if (pageState.value == 1) buttonColor else Color.Transparent, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Posted Task",
                        fontSize = 12.sp,
                        color = if (pageState.value == 1) Color.Black else MaterialTheme.colors.onSurface
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 4.dp)
                        .clickable { pageState.value = 2 }
                        .background(if (pageState.value == 2) buttonColor else Color.Transparent, RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "My Assign Task",
                        fontSize = 12.sp,
                        color = if (pageState.value == 2) Color.Black else MaterialTheme.colors.onSurface
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
        val userID = document.getString("userID") ?: ""
        var starRate = 0.0
        val querySnapshotUser = db.collection("User").whereEqualTo("id", userID).get().await()
        val b : Bitmap? = null
        val a = mutableStateOf(b)
        querySnapshotUser.documents.forEach { document ->
            starRate = document.getDouble("starRate") ?: 0.0
        }
        val avatarImagesRef = Firebase.storage.reference.child("avatar/"+userID+".jpg")
        avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
            a.value = BitmapFactory.decodeByteArray(it,0,it.size)
        }.addOnFailureListener {

        }
        if (user == document.getString("userID")) {
            postTaskList.add(
                TaskItem(
                    taskId = taskId,
                    taskName = taskTopic,
                    location = address,
                    date = date,
                    time = "$startTime - $endTime",
                    status = status,
                    bill = money,
                    imageUrl = a
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
        val userID = document.getString("userID") ?: ""
        var starRate = 0.0
        val querySnapshotUser = db.collection("User").whereEqualTo("id", userID).get().await()
        val b : Bitmap? = null
        val a = mutableStateOf(b)
        querySnapshotUser.documents.forEach { document ->
            starRate = document.getDouble("starRate") ?: 0.0
        }
        val avatarImagesRef = Firebase.storage.reference.child("avatar/"+userID+".jpg")
        avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
            a.value = BitmapFactory.decodeByteArray(it,0,it.size)
        }.addOnFailureListener {

        }
        if (user == document.getString("assignID")) {
            getTaskList.add(
                TaskItem(
                    taskId = taskId,
                    taskName = taskTopic,
                    location = address,
                    date = date,
                    time = "$startTime - $endTime",
                    status = status,
                    bill = money,
                    imageUrl = a
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
            val openDialog = remember { mutableStateOf(false) }
            DeleteTaskDialog(taskID = taskItem.taskId, openDialog = openDialog)
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
                                fontSize = 16.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.location,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 10.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.date,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 10.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.time,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 10.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.status,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 10.sp,
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
                        taskItem.imageUrl.value.let {
                            if (it != null) {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(80.dp)
                                )
                            }
                            else{
                                androidx.compose.material3.Icon(
                                    painter = painterResource(R.drawable.person),
                                    tint = Color.Black,
                                    contentDescription = "the person1",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clickable { }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .background(Color.Transparent)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    openDialog.value = true
                                }
                        )
                    }
                }
            }
        }
    }
}


