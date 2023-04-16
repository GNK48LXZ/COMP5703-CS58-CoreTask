package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


data class Information(
    val taskTopic: String? = null,
    val date: String? = null,
    val taskDescription: String? = null,
    val address: String? = null,
    val require: String? = null,
    val money: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val status: String = "open"
)

val FireStore = Firebase.firestore

// 步骤3：添加读取数据的监听器




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringDetails(taskId: String){
    var taskTopic by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var money by remember { mutableStateOf("") }
    var require by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        // 监听指定Document ID的数据
        FireStore.collection("Task")
            .document("E6mUK9QHjJs2leJt0acA")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // 处理错误
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        // 读取数据并设置到Text位置
                        taskTopic = snapshot.getString("taskTopic") ?: ""
                        address = snapshot.getString("address") ?: ""
                        date = snapshot.getString("date") ?: ""
                        startTime = snapshot.getString("startTime") ?: ""
                        endTime = snapshot.getString("endTime") ?: ""
                        money = snapshot.getString("money") ?: ""
                        taskDescription = snapshot.getString("taskDescription") ?: ""
                        require = snapshot.getString("require") ?: ""
                    }
                }

            }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    "Icon",
                    modifier = Modifier
                        .clickable { }
                        .padding(horizontal = 16.dp)
                        .size(30.dp),
                    tint = Color(0xff333333)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Task details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            Text(
                text = "Open",
                fontSize = 15.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = "Assigned",
                fontSize = 15.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = "Completed",
                fontSize = 15.sp,
                fontWeight = FontWeight.W500
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = taskTopic,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 33.sp,
                lineHeight = 40.sp,
                style = MaterialTheme.typography.bodyLarge,
            )
            //Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(70.dp)
                    .fillMaxWidth()
            ) {
                androidx.compose.material3.Icon(
                    Icons.Outlined.Person,
                    modifier = Modifier.size(70.dp),
                    contentDescription = "User",
                )
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "POSTED BY", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Jessica L",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
                Spacer(modifier = Modifier.padding(start = 300.dp))
                androidx.compose.material3.Icon(
                    painter = painterResource(R.drawable.chat),
                    tint = blackColor,
                    contentDescription = "chat",
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                )
            }
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.5.dp,
                color = textFieldColor,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(15.dp))
                    androidx.compose.material3.Icon(
                        Icons.Outlined.LocationOn,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Address",
                    )
                }
                Spacer(modifier = Modifier.width(28.dp))
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "LOCATION", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = address,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 25.dp),
                thickness = 1.5.dp,
                color = textFieldColor,
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .height(90.dp)
                    .fillMaxWidth()
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(15.dp))
                    androidx.compose.material3.Icon(
                        Icons.Outlined.DateRange,
                        modifier = Modifier.size(35.dp),
                        contentDescription = "Date",
                    )
                }
                Spacer(modifier = Modifier.width(28.dp))
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "TO BE DONE ON", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(//text = "Monday April 10",
                        text = date+"  "+startTime+"-"+endTime,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "TASK PRICE",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = money+"$",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    FilledTonalButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(21.dp),
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(buttonColor)
                    ) {
                        Text("Make an offer", fontSize = 20.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Task Detail",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = taskDescription,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Certificate the job seeker need",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = require,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,

                )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Offers",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.size(width = 500.dp, height = 50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */},
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Row(Modifier.padding(16.dp)
                    .fillMaxWidth())
                {
                    androidx.compose.material3.Icon(
                        painter = painterResource(R.drawable.person),
                        tint = blackColor,
                        contentDescription = "the person1"
                    )
                    Spacer(modifier = Modifier.width(60.dp))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color= blackColor,
                        text = "Ruby",
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.size(width = 500.dp, height = 50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */},
                colors = CardDefaults.cardColors(textFieldColor)
            ) {
                Row(Modifier.padding(16.dp))
                {
                    androidx.compose.material3.Icon(
                        painter = painterResource(R.drawable.person),
                        tint = blackColor,
                        contentDescription = "the cleaning"
                    )
                    Spacer(modifier = Modifier.width(60.dp))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color= blackColor,
                        text = "Ruby",
                    )
                }
            }
        }
    }
}
