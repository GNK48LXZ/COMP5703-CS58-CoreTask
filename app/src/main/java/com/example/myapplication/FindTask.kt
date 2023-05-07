package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await




@ExperimentalMaterial3Api
@Composable
fun FindTask(navController: NavController) {
    val options1 = listOf("All Task", "Cleaning", "Removals", "Repairs", "Painting")
    var selectedOption1 by remember { mutableStateOf(options1[0]) }
    val options2 = listOf("Bill", "0-50", "51-100", "100-200", "200~")
    var selectedOption2 by remember { mutableStateOf(options2[0]) }
    val options3 = listOf("Status", "Open", "Assigned")
    var selectedOption3 by remember { mutableStateOf(options3[0]) }

    var filterText by remember { mutableStateOf("") }
    val pageState = remember { mutableStateOf(1) }


    when (pageState.value) {
        1 -> {
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
                        text = "Browse Tasks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Poppins
                    )
                }
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
                                modifier = Modifier.clickable(onClick = { pageState.value = 6 })
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
                        pageState,
                        options = options1,
                        selectedOption = selectedOption1,
                        onOptionSelected = { option ->
                            selectedOption1 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options2,
                        selectedOption = selectedOption2,
                        onOptionSelected = { option ->
                            selectedOption2 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options3,
                        selectedOption = selectedOption3,
                        onOptionSelected = { option ->
                            selectedOption3 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                TaskListScreen("Task", navController)
            }
        }

        2 -> {
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
                        text = "Browse Tasks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Poppins
                    )
                }
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
                                modifier = Modifier.clickable(onClick = { pageState.value = 6 })
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
                        pageState,
                        options = options1,
                        selectedOption = selectedOption1,
                        onOptionSelected = { option ->
                            selectedOption1 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options2,
                        selectedOption = selectedOption2,
                        onOptionSelected = { option ->
                            selectedOption2 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options3,
                        selectedOption = selectedOption3,
                        onOptionSelected = { option ->
                            selectedOption3 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                TaskListOthersScreen("Task", navController, 1)
            }
        }

        3 -> {
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
                        text = "Browse Tasks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Poppins
                    )
                }
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
                                modifier = Modifier.clickable(onClick = { pageState.value = 6 })
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
                        pageState,
                        options = options1,
                        selectedOption = selectedOption1,
                        onOptionSelected = { option ->
                            selectedOption1 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options2,
                        selectedOption = selectedOption2,
                        onOptionSelected = { option ->
                            selectedOption2 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options3,
                        selectedOption = selectedOption3,
                        onOptionSelected = { option ->
                            selectedOption3 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                TaskListOthersScreen("Task", navController, 2)
            }
        }

        4 -> {
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
                        text = "Browse Tasks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Poppins
                    )
                }
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
                                modifier = Modifier.clickable(onClick = { pageState.value = 6 })
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
                        pageState,
                        options = options1,
                        selectedOption = selectedOption1,
                        onOptionSelected = { option ->
                            selectedOption1 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options2,
                        selectedOption = selectedOption2,
                        onOptionSelected = { option ->
                            selectedOption2 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options3,
                        selectedOption = selectedOption3,
                        onOptionSelected = { option ->
                            selectedOption3 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                TaskListOthersScreen("Task", navController, 3)
            }
        }

        5 -> {
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
                        text = "Browse Tasks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Poppins
                    )
                }
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
                                modifier = Modifier.clickable(onClick = { pageState.value = 6 })
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
                        pageState,
                        options = options1,
                        selectedOption = selectedOption1,
                        onOptionSelected = { option ->
                            selectedOption1 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options2,
                        selectedOption = selectedOption2,
                        onOptionSelected = { option ->
                            selectedOption2 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options3,
                        selectedOption = selectedOption3,
                        onOptionSelected = { option ->
                            selectedOption3 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                TaskListOthersScreen("Task", navController, 4)
            }
        }

        6 -> {
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
                        text = "Browse Tasks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = Poppins
                    )
                }
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
                                modifier = Modifier.clickable(onClick = { pageState.value = 6 })
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
                        pageState,
                        options = options1,
                        selectedOption = selectedOption1,
                        onOptionSelected = { option ->
                            selectedOption1 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options2,
                        selectedOption = selectedOption2,
                        onOptionSelected = { option ->
                            selectedOption2 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdown(
                        pageState,
                        options = options3,
                        selectedOption = selectedOption3,
                        onOptionSelected = { option ->
                            selectedOption3 = option
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                TaskListFilterScreen("Task", navController, filterText)
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
    val imageUrl: Int
)

@Composable
fun FilterDropdown(
    pageState: MutableState<Int>,
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
                                if (option == "All Task") {
                                    pageState.value = 1
                                }
                                if (option == "Cleaning") {
                                    pageState.value = 2
                                }
                                if (option == "Removals") {
                                    pageState.value = 3
                                }
                                if (option == "Repairs") {
                                    pageState.value = 4
                                }
                                if (option == "Painting") {
                                    pageState.value = 5
                                }
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
    classification: Int
) {
    var taskItems by remember { mutableStateOf(listOf<TaskItem>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(collectionPath) {
        taskItems = loadClassificationDataFromFirestore(
            db = db,
            collectionPath = collectionPath,
            classification = classification
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

public suspend fun loadClassificationDataFromFirestore(
    db: FirebaseFirestore,
    collectionPath: String,
    classification: Int
): List<TaskItem> {
    val taskList1 = mutableListOf<TaskItem>()
    val taskList2 = mutableListOf<TaskItem>()
    val taskList3 = mutableListOf<TaskItem>()
    val taskList4 = mutableListOf<TaskItem>()
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
                    imageUrl = imageUrl
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
                    imageUrl = imageUrl
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
                    imageUrl = imageUrl
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
                    imageUrl = imageUrl
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
    return taskList1 + taskList2 + taskList3 + taskList4
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
        val imageUrl = R.drawable.ic_launcher_foreground

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
                    imageUrl = imageUrl
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
                                fontSize = 12.sp,
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
                                fontSize = 12.sp,
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

