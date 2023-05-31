package com.example.myapplication


import No
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await


@ExperimentalMaterial3Api
@Composable
fun FindTask(navController: NavController) {

    val db = Firebase.firestore
    var notice by remember {
        mutableStateOf(false)
    }
    notice = Listen()
    if (notice == true) {
        No()
        db.collection("User").document(user).update("notice", false)
    }

    val options1 = listOf("All Task", "Cleaning", "Removals", "Repairs", "Painting")
    var selectedOption1 by remember { mutableStateOf(options1[0]) }
    val options2 = listOf("All Bill", "0-50", "51-100", "101~")
    var selectedOption2 by remember { mutableStateOf(options2[0]) }
    val options3 = listOf("All Status", "Open", "Assigned", "Completed")
    var selectedOption3 by remember { mutableStateOf(options3[0]) }

    var filterText by remember { mutableStateOf("") }
    val pageState = remember { mutableStateOf(1) }

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
                text = "Browse Tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = Poppins,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            androidx.compose.material.TextField(
                value = filterText,
                onValueChange = { filterText = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                textStyle = MaterialTheme.typography.body2,
                placeholder = { androidx.compose.material.Text("Filter by task name") },
                trailingIcon = {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search icon",
                        tint = Color.Gray,
                        modifier = Modifier.clickable(onClick = {
                            pageState.value = 6
                        })
                    )
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterDropdown(
                options = options1,
                selectedOption = selectedOption1,
                onOptionSelected = { option ->
                    selectedOption1 = option
                },
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                options = options2,
                selectedOption = selectedOption2,
                onOptionSelected = { option ->
                    selectedOption2 = option
                },
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                options = options3,
                selectedOption = selectedOption3,
                onOptionSelected = { option ->
                    selectedOption3 = option
                },
                modifier = Modifier.weight(1f)
            )
        }

        /*if (selectedOption1 == "All Task") {
            TaskListScreen("Task", navController)
        } else if (selectedOption1 == "Cleaning") {
            TaskListOthersScreen("Task", navController, 1)
        } else if (selectedOption1 == "Removals") {
            TaskListOthersScreen("Task", navController, 2)
        } else if (selectedOption1 == "Repairs") {
            TaskListOthersScreen("Task", navController, 3)
        } else if (selectedOption1 == "Painting") {
            TaskListOthersScreen("Task", navController, 4)
        }
        if (selectedOption2 == "All Bill") {
            TaskListScreen("Task", navController)
        } else if (selectedOption2 == "0-50") {
            TaskListOthersScreen("Task", navController, 5)
        } else if (selectedOption2 == "51-100") {
            TaskListOthersScreen("Task", navController, 6)
        } else if (selectedOption2 == "101-200") {
            TaskListOthersScreen("Task", navController, 7)
        } else if (selectedOption2 == "200~") {
            TaskListOthersScreen("Task", navController, 8)
        }
        if (selectedOption3 == "All Status") {
            TaskListScreen("Task", navController)
        } else if (selectedOption3 == "Open") {
            TaskListOthersScreen("Task", navController, 9)
        } else if (selectedOption3 == "Assigned") {
            TaskListOthersScreen("Task", navController, 10)
        } else if (selectedOption3 == "Completed") {
            TaskListOthersScreen("Task", navController, 11)
        }
        if (pageState.value == 6) {
            TaskListFilterScreen("Task", navController, filterText = filterText)
        }*/
        when {
            pageState.value == 1 -> {
                when {
                    selectedOption1 == "All Task" && selectedOption2 == "All Bill" && selectedOption3 == "All Status" -> {
                        TaskListScreen("Task", navController)
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "All Bill" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 0)
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "0-50" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 1)
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "0-50" && selectedOption3 == "Open" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 1, "Open")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "0-50" && selectedOption3 == "Assigned" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 1, "Assigned")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "0-50" && selectedOption3 == "Completed" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 1, "Completed")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "51-100" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 2)
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "51-100" && selectedOption3 == "Open" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 2, "Open")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "51-100" && selectedOption3 == "Assigned" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 2, "Assigned")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "51-100" && selectedOption3 == "Completed" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 2, "Completed")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "101~" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 3)
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "101~" && selectedOption3 == "Open" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 3, "Open")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "101~" && selectedOption3 == "Assigned" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 3, "Assigned")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "101~" && selectedOption3 == "Completed" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 3, "Completed")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "All Bill" && selectedOption3 == "Open" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 0, "Open")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "All Bill" && selectedOption3 == "Assigned" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 0, "Assigned")
                    }

                    selectedOption1 == "Cleaning" && selectedOption2 == "All Bill" && selectedOption3 == "Completed" -> {
                        TaskListOthersScreen("Task", navController, "Cleaning", 0, "Completed")
                    }

                    /*selectedOption1 == "Removals" && selectedOption2 == "All Bill" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, 2)
                    }

                    selectedOption1 == "Repairs" && selectedOption2 == "All Bill" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, 3)
                    }

                    selectedOption1 == "Painting" && selectedOption2 == "All Bill" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, 4)
                    }

                    selectedOption2 == "0-50" && selectedOption1 == "All Task" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, 5)
                    }

                    selectedOption2 == "51-100" && selectedOption1 == "All Task" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, 6)
                    }

                    selectedOption2 == "101~" && selectedOption1 == "All Task" && selectedOption3 == "All Status" -> {
                        TaskListOthersScreen("Task", navController, 7)
                    }

                    selectedOption3 == "Open" && selectedOption1 == "All Task" && selectedOption2 == "All Bill" -> {
                        TaskListOthersScreen("Task", navController, 9)
                    }

                    selectedOption3 == "Assigned" && selectedOption1 == "All Task" && selectedOption2 == "All Bill" -> {
                        TaskListOthersScreen("Task", navController, 10)
                    }

                    selectedOption3 == "Completed" && selectedOption1 == "All Task" && selectedOption2 == "All Bill" -> {
                        TaskListOthersScreen("Task", navController, 11)
                    }

                    pageState.value == 6 && selectedOption1 == "All Task" && selectedOption2 == "All Bill" && selectedOption3 == "All Status" -> {
                        TaskListFilterScreen("Task", navController, filterText = filterText)
                    }*/
                }
            }

            pageState.value == 6 -> {
                TaskListFilterScreen("Task", navController, filterText = filterText)
            }
        }

    }
}

data class TaskItem(
    val taskId: String,
    val taskName: String,
    val location: String,
    val date: String,
    val time: String,
    val status: String,
    val bill: String,
    val imageUrl: MutableState<Bitmap?>
)

@Composable
fun FilterDropdown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        val selectedItemModifier = Modifier.selectable(
            selected = expanded,
            onClick = { expanded = true },
            role = Role.Button
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = selectedItemModifier) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.padding(2.dp)
            )
            androidx.compose.material.Text(
                text = selectedOption,
                modifier = Modifier.padding(4.dp)
            )
        }
        androidx.compose.material.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
            content = {
                options.forEach { option ->
                    androidx.compose.material.DropdownMenuItem(
                        modifier = Modifier.selectable(
                            selected = (option == selectedOption),
                            onClick = {
                                onOptionSelected(option)
                                expanded = false
                            }
                        ),
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    ) {
                        androidx.compose.material.Text(option)
                    }
                }
            }
        )
    }
}

@Composable
fun TaskListScreen(collectionPath: String, navController: NavController) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadDataFromFirestore(db, collectionPath)
    }

    TaskListLazyColumn(taskItems, navController)
}

@Composable
fun showShearchingPage(collectionPath: String, navController: NavController, filterText: String) {
    TaskListFilterScreen(collectionPath, navController, filterText = filterText)
}

@Composable
fun TaskListFilterScreen(collectionPath: String, navController: NavController, filterText: String) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadSearchDataFromFirestore(db, collectionPath, filterText)
    }

    TaskListLazyColumn(taskItems, navController)
}

@Composable
fun TaskListOthersScreen(
    collectionPath: String,
    navController: NavController,
    classification: String,
    Bill: Int,
    Status: String
) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadSeletedDataFromFirestore(
            db = db,
            classification = classification,
            Bill = Bill,
            Status = Status
        )
    }

    TaskListLazyColumn(taskItems, navController)
}

@Composable
fun TaskListOthersScreen(
    collectionPath: String,
    navController: NavController,
    Bill: Int,
    Status: String
) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadSeletedDataFromFirestoreAT(
            db = db,
            Bill = Bill,
            Status = Status
        )
    }
    TaskListLazyColumn(taskItems, navController)
}

@Composable
fun TaskListOthersScreen(
    collectionPath: String,
    navController: NavController,
    classification: String,
    Bill: Int,
) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadSeletedDataFromFirestoreAS(
            db = db,
            classification = classification,
            Bill = Bill
        )
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
        val b: Bitmap? = null
        val a = mutableStateOf(b)
        querySnapshotUser.documents.forEach { document ->
            starRate = document.getDouble("starRate") ?: 0.0
        }
        val avatarImagesRef = Firebase.storage.reference.child("avatar/" + userID + ".jpg")
        avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
            a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
        }.addOnFailureListener {

        }
        taskList.add(
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
    return taskList
}
/*

public suspend fun loadClassificationDataFromFirestore(
    db: FirebaseFirestore,
    collectionPath: String,
    classification: Int
): List<TaskItem> {
    val taskList1 = mutableListOf<TaskItem>()
    val taskList2 = mutableListOf<TaskItem>()
    val taskList3 = mutableListOf<TaskItem>()
    val taskList4 = mutableListOf<TaskItem>()
    val taskList5 = mutableListOf<TaskItem>()
    val taskList6 = mutableListOf<TaskItem>()
    val taskList7 = mutableListOf<TaskItem>()
    val taskList8 = mutableListOf<TaskItem>()
    val taskList9 = mutableListOf<TaskItem>()
    val taskList10 = mutableListOf<TaskItem>()
    val taskList11 = mutableListOf<TaskItem>()
    val taskList12 = mutableListOf<TaskItem>()
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
        val b: Bitmap? = null
        val a = mutableStateOf(b)
        querySnapshotUser.documents.forEach { document ->
            starRate = document.getDouble("starRate") ?: 0.0
        }
        val avatarImagesRef = Firebase.storage.reference.child("avatar/" + userID + ".jpg")
        avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
            a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
        }.addOnFailureListener {

        }
        val classification = document.getString("classification")


        if ("Cleaning" == classification) {
            taskList1.add(
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
        if ("Cleaning" == classification && money.toInt() <= 50) {
            taskList12.add(
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
        if ("Cleaning" == classification && money.toInt() in 51..100) {
            taskList13.add(
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
        if ("Cleaning" == classification && money.toInt() > 100) {
            taskList14.add(
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
        if ("Cleaning" == classification && money.toInt() <= 50 && status == "Open") {
            taskList15.add(
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
        if ("Cleaning" == classification && money.toInt() <= 50 && status == "Assigned") {
            taskList16.add(
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
        if ("Cleaning" == classification && money.toInt() <= 50 && status == "Completed") {
            taskList17.add(
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
        if ("Cleaning" == classification && money.toInt() in 51..100 && status == "Open") {
            taskList18.add(
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
        if ("Removals" == classification) {
            taskList2.add(
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
        if ("Repairs" == classification) {
            taskList3.add(
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
        if (classification == "Painting") {
            taskList4.add(
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
        if (money.toInt() <= 50) {
            taskList5.add(
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
        if (money.toInt() in 51..100) {
            taskList6.add(
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
        if (money.toInt() > 100) {
            taskList7.add(
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

        if (status == "Open") {
            taskList9.add(
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
        if (status == "Assigned") {
            taskList10.add(
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
        if (status == "Completed") {
            taskList11.add(
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
    if (classification == 1) {
        return taskList1
    }
    if (classification == 2) {
        return taskList2
    }
    if (classification == 3) {
        return taskList3
    }
    if (classification == 4) {
        return taskList4
    }
    if (classification == 5) {
        return taskList5
    }
    if (classification == 6) {
        return taskList6
    }
    if (classification == 7) {
        return taskList7
    }
    if (classification == 8) {
        return taskList8
    }
    if (classification == 9) {
        return taskList9
    }
    if (classification == 10) {
        return taskList10
    }
    if (classification == 11) {
        return taskList11
    }

    return taskList1 + taskList2 + taskList3 + taskList4
}
*/

public suspend fun loadSeletedDataFromFirestoreAT(
    db: FirebaseFirestore,
    Bill: Int,
    Status: String
): List<TaskItem> {
    val taskList = mutableListOf<TaskItem>()
    if (Bill == 0) {
        db.collection("Task")
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                taskList.add(
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
        return taskList
    } else if (Bill == 1) {
        db.collection("Task")
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() <= 50) {
                    taskList.add(
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
        return taskList
    } else if (Bill == 2) {
        db.collection("Task")
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() in 51..100) {
                    taskList.add(
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
        return taskList
    } else if (Bill == 3) {
        db.collection("Task")
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() > 100) {
                    taskList.add(
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
        return taskList
    }
    return taskList
}

public suspend fun loadSeletedDataFromFirestoreAS(
    db: FirebaseFirestore,
    classification: String,
    Bill: Int,
): List<TaskItem> {
    val taskList = mutableListOf<TaskItem>()
    if (Bill == 0) {
        db.collection("Task").whereEqualTo("classification", classification).get()
            .await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                taskList.add(
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
        return taskList
    } else if (Bill == 1) {
        db.collection("Task").whereEqualTo("classification", classification).get()
            .await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() <= 50) {
                    taskList.add(
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
        return taskList
    } else if (Bill == 2) {
        db.collection("Task").whereEqualTo("classification", classification).get()
            .await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() in 51..100) {
                    taskList.add(
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
        return taskList
    } else if (Bill == 3) {
        db.collection("Task").whereEqualTo("classification", classification).get()
            .await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() > 100) {
                    taskList.add(
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
        return taskList
    }
    return taskList
}

public suspend fun loadSeletedDataFromFirestore(
    db: FirebaseFirestore,
    classification: String,
    Bill: Int,
    Status: String
): List<TaskItem> {
    val taskList = mutableListOf<TaskItem>()
    if (Bill == 0) {
        db.collection("Task").whereEqualTo("classification", classification)
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                taskList.add(
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
        return taskList
    } else if (Bill == 1) {
        db.collection("Task").whereEqualTo("classification", classification)
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() <= 50) {
                    taskList.add(
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
        return taskList
    } else if (Bill == 2) {
        db.collection("Task").whereEqualTo("classification", classification)
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() in 51..100) {
                    taskList.add(
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
        return taskList
    } else if (Bill == 3) {
        db.collection("Task").whereEqualTo("classification", classification)
            .whereEqualTo("status", Status).get().await().documents.forEach { document ->
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
                val querySnapshotUser =
                    db.collection("User").whereEqualTo("id", userID).get().await()
                val b: Bitmap? = null
                val a = mutableStateOf(b)
                querySnapshotUser.documents.forEach { document ->
                    starRate = document.getDouble("starRate") ?: 0.0
                }
                val avatarImagesRef =
                    Firebase.storage.reference.child("avatar/" + userID + ".jpg")
                avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                    a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                }.addOnFailureListener {

                }
                if (money.toInt() > 100) {
                    taskList.add(
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
        return taskList
    }
    return taskList
}

public suspend fun loadSearchDataFromFirestore(
    db: FirebaseFirestore,
    collectionPath: String,
    filterText: String
): List<TaskItem> {
    val taskList = mutableListOf<TaskItem>()
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
        val b: Bitmap? = null
        val a = mutableStateOf(b)
        querySnapshotUser.documents.forEach { document ->
            starRate = document.getDouble("starRate") ?: 0.0
        }
        val avatarImagesRef = Firebase.storage.reference.child("avatar/" + userID + ".jpg")
        avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
            a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
        }.addOnFailureListener {

        }

        if (taskTopic.contains(filterText, ignoreCase = true)) {
            taskList.add(
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
                    .clickable {
                        navController.navigate("monitoringDetails/${taskItem.taskId}")
                    }
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
                                fontSize = 12.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.date,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 12.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            taskItem.time,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 12.sp,
                                fontFamily = Poppins
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                        taskItem.status.let {
                            if (it == "Completed") {
                                Text(
                                    taskItem.status,
                                    style = MaterialTheme.typography.body1.copy(
                                        fontSize = 15.sp,
                                        fontFamily = Poppins
                                    ),
                                    color = Color.Red,
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                )
                            } else if (it == "Assigned") {
                                Text(
                                    taskItem.status,
                                    style = MaterialTheme.typography.body1.copy(
                                        fontSize = 15.sp,
                                        fontFamily = Poppins
                                    ),
                                    color = lightGreen,
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                )
                            } else {
                                Text(
                                    taskItem.status,
                                    style = MaterialTheme.typography.body1.copy(
                                        fontSize = 15.sp,
                                        fontFamily = Poppins
                                    ),
                                    color = Color.Blue,
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .background(color = background),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "AU " + taskItem.bill + " $",
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(bottom = 30.dp)
                        )

                        taskItem.imageUrl.value.let {
                            if (it != null) {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(50.dp)
                                )
                            } else {
                                androidx.compose.material3.Icon(
                                    painter = painterResource(R.drawable.person),
                                    tint = Color.Black,
                                    contentDescription = "the person1",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

