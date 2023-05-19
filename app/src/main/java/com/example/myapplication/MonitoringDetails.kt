package com.example.myapplication

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.tasks.await
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
    val status: String = "Open",
    //val UserID: String? = null
)

val FireStore = Firebase.firestore

// 步骤3：添加读取数据的监听器

data class OfferItem(
    val recommendation: String? = null,
    val userID: String? = null,
    val taskID: String? = null,
    val userName: String? = null,
    var status: String,
    val offerID: String,
    val starRate: Double = 0.0,
    val avatar: MutableState<Bitmap?>
    //val time: String,
    //val imageUrl: Int
)


public suspend fun loadOfferDataFromFirestore(
    db: FirebaseFirestore,
    collectionPath: String,
    taskId: String
): List<OfferItem> {
    val offerList = mutableListOf<OfferItem>()
    val querySnapshot = db.collection(collectionPath).whereEqualTo("taskID", taskId).get().await()
    val storage = Firebase.storage
    var storageRef = storage.reference
    querySnapshot.documents.forEach { document ->
        val recommendation = document.getString("recommendation") ?: ""
        val taskID = document.getString("taskID") ?: ""
        val userID = document.getString("userID") ?: ""
        var userName = ""
        val querySnapshotUserName = db.collection("User").whereEqualTo("id", userID).get().await()
        var status = document.getString("status") ?: ""
        val offerID = document.id
        var starRate = 0.0
        val querySnapshotUser = db.collection("User").whereEqualTo("id", userID).get().await()
        val b: Bitmap? = null
        val a = mutableStateOf(b)
        querySnapshotUser.documents.forEach { document ->
            starRate = document.getDouble("starRate") ?: 0.0
        }
        val avatarImagesRef = storageRef.child("avatar/" + userID + ".jpg")
        avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
            a.value = BitmapFactory.decodeByteArray(it, 0, it.size)
        }.addOnFailureListener {

        }
        querySnapshotUserName.documents.forEach { document ->
            userName = document.getString("name") ?: ""
        }
        offerList.add(
            OfferItem(
                recommendation = recommendation,
                userID = userID,
                taskID = taskID,
                userName = userName,
                status = status,
                offerID = offerID,
                starRate = starRate,
                avatar = a
            )
        )
    }
    return offerList
}


@Composable
fun OfferListLazyColumn(
    offerItem: List<OfferItem>,
    userId: String?,
    taskId: String,
    navController: NavController
) {
    var taskstatus by remember { mutableStateOf("") }
    LaunchedEffect("Task") {
        FireStore.collection("Task")
            .document(taskId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        taskstatus = snapshot.getString("status") ?: ""
                    }
                }
            }
    }

    LazyColumn(
        modifier = Modifier
            .background(background)
            .fillMaxWidth()
            .height(250.dp),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(offerItem) { offerItem ->
            // 对于每个 Offer 进行 UI 的渲染
            // ...
            val openDialog = remember { mutableStateOf(false) }
            DeleteOfferDialog(offerID = offerItem.offerID, openDialog = openDialog)
            Card(
                modifier = Modifier
                    .height(150.dp)
                    .padding(horizontal = 16.dp)
                    .clickable(userId == user) { navController.navigate("OfferDetails/${offerItem.recommendation}/${offerItem.userID}") },
                colors = CardDefaults.cardColors(textFieldColor),
            ) {
                Column(
                    Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                    {
                        offerItem.avatar.value.let {
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
                                        .clickable { }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Column {
                            offerItem.userName?.let {
                                Text(
                                    it,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.W500,
                                    color = Color.Black,
                                )
                            }
                            StarRate(offerItem.starRate)
                        }
                        Spacer(modifier = Modifier.width(35.dp))
                        if (offerItem.userID == user) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.Delete,
                                "Icon",
                                modifier = Modifier
                                    .clickable {
                                        openDialog.value = true
                                    }
                                    .padding(horizontal = 10.dp)
                                    .size(40.dp),
                                tint = Color(0xff333333)
                            )
                        }
                    }
                    offerItem.recommendation?.let {
                        Text(
                            "$it",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clip(MaterialTheme.shapes.medium)
                        )
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(bottom = 5.dp)
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            val db = FirebaseFirestore.getInstance()
                            if (userId == user) {
                                Button(
                                    onClick = {
                                        db.collection("Offer").document(offerItem.offerID)
                                            .update("status", "Assigned")
                                        db.collection("Task").document(taskId)
                                            .update("status", "Assigned")
                                        db.collection("Task").document(taskId)
                                            .update("assignID", offerItem.userID)
                                            .addOnSuccessListener {

                                            }
                                    },
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(130.dp),
                                    colors = ButtonDefaults.buttonColors(buttonColor),
                                    enabled = when (taskstatus) {
                                        "Assigned", "Completed" -> false
                                        "Open" -> true
                                        else -> true // 默认情况下，按钮不可点击
                                    }
                                ) {
                                    if (offerItem.status == "Assigned") {
                                        Text(
                                            text = "Accepted",
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.W500
                                        )
                                    } else {
                                        Text(
                                            text = "Accept",
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.W500
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OfferDetails(recommendation: String, userID: String, navController: NavController) {
    var starrate by remember { mutableStateOf(0.0) }
    LaunchedEffect(Unit) {
        FireStore.collection("User")
            .document(userID)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // 处理错误
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        starrate = snapshot.getDouble("starRate") ?: 0.0
                    }
                }
            }
    }
    var userName by remember { mutableStateOf("") }
    Firebase.firestore.collection("User").whereEqualTo("id", userID).get()
        .addOnSuccessListener { querySnapshot ->
            for (documentSnapshot in querySnapshot.documents) {
                userName = documentSnapshot.getString("name") ?: ""
            }
        }
    val storage = Firebase.storage
    var storageRef = storage.reference
    val avatarImagesRef = storageRef.child("avatar/" + userID + ".jpg")
    val avatar = remember {
        mutableStateOf<Bitmap?>(null)
    }
    avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
        avatar.value = BitmapFactory.decodeByteArray(it, 0, it.size)
    }.addOnFailureListener {
        // Handle any errors
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Offer Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            avatar.value.let {
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
                            .clickable { }
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = userName,
                    modifier = Modifier,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                StarRate(starrate)
            }
        }
        Text(
            text = "Certificate",
            fontSize = 25.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 30.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.5.dp,
            color = textFieldColor,
        )
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = "Recommendation",
            fontSize = 25.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 30.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.5.dp,
            color = textFieldColor,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = recommendation,
            fontSize = 15.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringDetails(taskId: String, navController: NavController) {
    var taskTopic by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var money by remember { mutableStateOf("") }
    var require by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var assignID by remember { mutableStateOf("") }
    var UserID by remember { mutableStateOf("") }
    var UserName by remember { mutableStateOf("") }
    var avatar: MutableState<Bitmap?> = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val storage = Firebase.storage
    var storageRef = storage.reference
    val openDialog = remember { mutableStateOf(false) }
    CompletedDialog(taskId = taskId, assignId = assignID, openDialog = openDialog, navController)

    LaunchedEffect("Task") {
        FireStore.collection("Task")
            .document(taskId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        taskTopic = snapshot.getString("taskTopic") ?: ""
                        address = snapshot.getString("address") ?: ""
                        date = snapshot.getString("date") ?: ""
                        startTime = snapshot.getString("startTime") ?: ""
                        endTime = snapshot.getString("endTime") ?: ""
                        money = snapshot.getString("money") ?: ""
                        taskDescription = snapshot.getString("taskDescription") ?: ""
                        require = snapshot.getString("require") ?: ""
                        assignID = snapshot.getString("assignID") ?: ""
                        UserID = snapshot.getString("userID") ?: ""
                        status = snapshot.getString("status") ?: ""
                        val avatarImagesRef = storageRef.child("avatar/" + UserID + ".jpg")
                        avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
                            avatar.value = BitmapFactory.decodeByteArray(it, 0, it.size)
                        }.addOnFailureListener {

                        }
                    }
                }
            }
    }

    Firebase.firestore.collection("User").whereEqualTo("id", UserID).get()
        .addOnSuccessListener { querySnapshot ->
            for (documentSnapshot in querySnapshot.documents) {
                UserName = documentSnapshot.getString("name") ?: ""
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
                        .clickable {
                            //navController.popBackStack()
                            navController.navigate(Screen.GetItDone.route)
                            page.value = 3
                        }
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
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .background(
                        color = when (status) {
                            "Open" -> Color(0xFF9BEDAD)
                            else -> Color.Transparent
                        },
                        shape = RoundedCornerShape(20.dp)
                    )

            ) {
                Text(
                    text = "Open",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = when (status) {
                        "Open" -> Color.Black
                        else -> Color.Black
                    }
                )
            }
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .background(
                        color = when (status) {
                            "Assigned" -> buttonColor
                            else -> Color.Transparent
                        },
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                Text(
                    text = "Assigned",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = when (status) {
                        "Assigned" -> Color.White
                        else -> Color.Black
                    },
                    modifier = Modifier.padding(5.dp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .background(
                        color = when (status) {
                            "Completed" -> Color(0xFFFF0000)
                            else -> Color.Transparent
                        },
                        shape = RoundedCornerShape(20.dp)
                    )

            ) {
                Text(
                    text = "Completed",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = when (status) {
                        "Completed" -> Color.White
                        else -> Color.Black
                    },
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = taskTopic,
                modifier = Modifier.padding(horizontal = 16.dp).padding(vertical = 8.dp),
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
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    avatar.value.let {
                        if (it != null) {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(50.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "POSTED BY", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(
                            text = UserName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(130.dp))
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(R.drawable.chat),
                                tint = Color.Black,
                                contentDescription = "chat",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable { }.padding(end = 5.dp)
                            )
                        }
                    }
                }
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
                    .height(85.dp)
                    .fillMaxWidth()
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
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
                        fontSize = 18.sp
                    )
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.5.dp,
                color = textFieldColor,
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .height(85.dp)
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
                        text = date + "  " + startTime + " - " + endTime,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

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
                        "TASK BUDGET",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = money + " AU$/hour",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (UserID == user) {
                        FilledTonalButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(21.dp),
                            onClick = { navController.navigate("EditTask/${taskId}") },
                            colors = ButtonDefaults.buttonColors(buttonColor),
                            enabled = when (status) {
                                "Completed" -> false
                                "Assigned", "Open" -> true
                                else -> true
                            }
                        ) {
                            Text("Change", fontSize = 20.sp)
                        }
                    } else {
                        FilledTonalButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(21.dp),
                            onClick = { navController.navigate("MakeAnOffer/${taskId}/${UserID}") },
                            colors = ButtonDefaults.buttonColors(buttonColor),
                            enabled = when (status) {
                                "Assigned", "Completed" -> false
                                "Open" -> true
                                else -> true
                            }
                        ) {
                            Text("Make an offer", fontSize = 20.sp)
                        }
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
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            //OfferListScreen("Offer")
            var offerItems by remember { mutableStateOf(listOf<OfferItem>()) }
            //var offerItems by remember { mutableListOf<OfferItem>() }
            //var offerItems = mutableListOf<OfferItem>()
            val db = FirebaseFirestore.getInstance()

            LaunchedEffect("Offer") {
                offerItems = loadOfferDataFromFirestore(db, "Offer", taskId)
            }
            OfferListLazyColumn(offerItems, userId = UserID, taskId = taskId, navController)
            if (status == "Assigned" && UserID == user) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(16.dp)
                        .background(background),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = { openDialog.value = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(buttonColor)
                        ) {
                            Text("Completed")
                        }
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTask(taskId: String, navController: NavController) {
    var money by remember { mutableStateOf("") }
    var require by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val storage = Firebase.storage

    LaunchedEffect(Unit) {
        // 监听指定Document ID的数据
        FireStore.collection("Task")
            .document(taskId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // 处理错误
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        money = snapshot.getString("money") ?: ""
                        require = snapshot.getString("require") ?: ""
                        taskDescription = snapshot.getString("taskDescription") ?: ""
                        address = snapshot.getString("address") ?: ""
                    }
                }
            }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit Task details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        androidx.compose.material3.Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(background)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                "taskDescription",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            Text(
                "Address",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            Text(
                "Require",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = require,
                onValueChange = { require = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )

            Text(
                "money",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = money,
                onValueChange = { money = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    val db = Firebase.firestore
                    val edidTask = db.collection("Task").document(taskId)
                    edidTask.update("address", address)
                    edidTask.update("require", require)
                    edidTask.update("taskDescription", taskDescription)
                    edidTask.update("money", money)
                    //navController.navigate("editDate/${taskId}")
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Continue", fontSize = 20.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editDate(
    taskId: String, navController: NavController
) {
    var startDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    val startCalendarState = rememberSheetState()
    val startClockState = rememberSheetState()
    val endClockState = rememberSheetState()
    val openDialog = remember { mutableStateOf(false) }
    DateDialog(openDialog)
    CalendarDialog(
        state = startCalendarState,
        selection = CalendarSelection.Date {
            startDate = it.toString()
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        )
    )
    ClockDialog(
        state = startClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            startTime = "$hours:$minutes"
        }
    )
    ClockDialog(
        state = endClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            endTime = "$hours:$minutes"
        }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Choose a time",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(40.dp))

        //Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "Date",
            style = MaterialTheme.typography.headlineLarge,
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //text = "Tell the job seeker your preferred time",
            text = "When do you want to start?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
            //lineHeight = 40.sp,
            //fontSize = 40.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable { startCalendarState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Date:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = startDate,
                    fontSize = 20.sp,
                    color = buttonColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable { startClockState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "From:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = startTime,
                    fontSize = 20.sp,
                    color = buttonColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .clickable { endClockState.show() },
            colors = CardDefaults.cardColors(textFieldColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "To:  ",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = endTime,
                    fontSize = 20.sp,
                    color = buttonColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(background),
            verticalArrangement = Arrangement.Bottom
        ) {
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(buttonColor),
                onClick = {
                    val db = Firebase.firestore
                    val edidDate = db.collection("Task").document(taskId)
                    edidDate.update("startTime", startTime)
                    edidDate.update("endTime", endTime)
                    edidDate.update("date", startDate)
                    navController.navigate("monitoringDetails/${taskId}")
                }
            ) {
                Text("Save", fontSize = 20.sp)
            }
        }
    }
}
