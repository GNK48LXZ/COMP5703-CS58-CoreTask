package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
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
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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
    val status: String = "open",
    //val UserID: String? = null
)

val FireStore = Firebase.firestore

// 步骤3：添加读取数据的监听器

data class OfferItem(
    val recommendation: String? = null,
    val userID: String? = null,
    val taskID: String? = null,
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
        var status = document.getString("status") ?: ""
        val offerID = document.id
        var starRate = 0.0
        val querySnapshotUser = db.collection("User").whereEqualTo("id", userID).get().await()
        val b : Bitmap? = null
        val a = mutableStateOf(b)
        querySnapshotUser.documents.forEach { document ->
            starRate = document.getDouble("starRate") ?: 0.0
        }
        val avatarImagesRef = storageRef.child("avatar/"+userID+".jpg")
        avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
            a.value = BitmapFactory.decodeByteArray(it,0,it.size)
        }.addOnFailureListener {

        }
        offerList.add(OfferItem(
            recommendation = recommendation,
            userID = userID,
            taskID = taskID,
            status = status,
            offerID = offerID,
            starRate = starRate,
            avatar = a
        ))
    }
    return offerList
}
@Composable
fun OfferListLazyColumn(offerItem: List<OfferItem>,userId: String?,taskId: String) {
    data class ButtonState(
        val isClickable: Boolean,
        val text: String
    )
    LazyColumn(
        modifier = Modifier
            .background(background)
            .fillMaxWidth()
            .height(500.dp),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(offerItem) { offerItem ->
            // 对于每个 Offer 进行 UI 的渲染
            // ...
            val openDialog = remember { mutableStateOf(false) }
            val offerStatus = MutableLiveData<String>()
            var isButtonClicked by remember { mutableStateOf(false) }
            var isButtonClickable by remember { mutableStateOf(true) }
            var buttonState by remember { mutableStateOf(mutableMapOf<String, ButtonState>()) }
            DeleteOfferDialog(offerID = offerItem.offerID, openDialog = openDialog)
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */ },
                colors = CardDefaults.cardColors(textFieldColor)
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
                            }
                            else{
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
                            offerItem.userID?.let {
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
                        if(offerItem.userID == user){
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
                            "\"$it\"",
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
                                        if (isButtonClickable) {
                                            db.collection("Offer").document(offerItem.offerID)
                                                .update("status", "Assigned")
                                                .addOnSuccessListener {
                                                    // 更新ViewModel中的状态值
                                                    offerStatus.value = "Assigned"
                                                    db.collection("Task").document(taskId)
                                                        .update("status", "Assigned")
                                                }
                                            isButtonClicked = true
                                            isButtonClickable = false
                                            buttonState = buttonState.mapValues {
                                                if (it.key == offerItem.offerID) {
                                                    ButtonState(false, "Accepted")
                                                } else {
                                                    ButtonState(false, it.value.text)
                                                }
                                            } as MutableMap<String, ButtonState>
                                        }
                                    },
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(100.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        if (isButtonClicked) Color.Gray else buttonColor
                                    ),
                                    enabled = isButtonClickable // 将按钮禁用除非offer已被分配
                                ) {
                                    if (isButtonClicked) {
                                        Text(
                                            text = "Accepted",
                                            lineHeight = 20.sp,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.W500
                                        )
                                    } else {Text(
                                        text = "Accept",
                                        lineHeight = 20.sp,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.W500
                                    ) }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringDetails(taskId: String,navController: NavController) {
    var taskTopic by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var money by remember { mutableStateOf("") }
    var require by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var UserID by remember { mutableStateOf("") }
    var avatar : MutableState<Bitmap?> = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val storage = Firebase.storage
    var storageRef = storage.reference

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
                        UserID = snapshot.getString("userID") ?: ""
                        status= snapshot.getString("status") ?: ""
                        val avatarImagesRef = storageRef.child("avatar/"+UserID+".jpg")
                        avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
                            avatar.value = BitmapFactory.decodeByteArray(it,0,it.size)
                        }.addOnFailureListener {

                        }
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
                        .clickable { navController.popBackStack() }
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = "Open",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .background(
                            color = when (status) {
                                "open" -> Color.Blue // 椭圆形蓝色
                                else -> Color.Transparent // 没有背景颜色
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                )
            }
            Box(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = "Assigned",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .background(
                            color = when (status) {
                                "Assigned" -> Color.Blue // 椭圆形蓝色
                                else -> Color.Transparent // 没有背景颜色
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                )
            }
            Box(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = "Completed",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .background(
                            color = when (status) {
                                "Completed" -> Color.Blue // 椭圆形蓝色
                                else -> Color.Transparent // 没有背景颜色
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
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
                Spacer(modifier = Modifier.width(20.dp))
                Column() {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "POSTED BY", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(
                            text = UserID,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(100.dp))
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            contentAlignment = Alignment.BottomEnd) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(R.drawable.chat),
                                tint = Color.Black,
                                contentDescription = "chat",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { }
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
                        text = date + "  " + startTime + "-" + endTime,
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
                        text = money + "$",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (UserID == user) {
                        FilledTonalButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(21.dp),
                            onClick = { /* 处理 Change 按钮的单击事件 */ },
                            colors = ButtonDefaults.buttonColors(buttonColor)
                        ) {
                            Text("Change", fontSize = 20.sp)
                        }
                    } else {
                        FilledTonalButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(21.dp),
                            onClick = { navController.navigate("MakeAnOffer/${taskId}") },
                            colors = ButtonDefaults.buttonColors(buttonColor)
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
                offerItems = loadOfferDataFromFirestore(db, "Offer",taskId)
            }
            OfferListLazyColumn(offerItems,userId= UserID, taskId =taskId)
            if(status=="Assigned" && UserID == user) {
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
                            onClick = { db.collection("Task").document(taskId)
                                .update("status", "Completed")},
                            modifier = Modifier.fillMaxWidth()
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
