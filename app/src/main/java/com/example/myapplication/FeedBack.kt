package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

data class Feedback(
    var feedback: String?,
    var posterID: String,
    var seekerID: String,
    var starRate: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBack(
    taskId: String,
    assignId: String,
    navController: NavController){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(background)
    ) {
        val db = Firebase.firestore
        var starRate by remember { mutableStateOf(0.0) }
        var feedbackCount by remember { mutableStateOf(0.0) }
        var newRate by remember { mutableStateOf(0.0) }
        var assignID by remember { mutableStateOf("") }
        var taskTopic by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            // 监听指定Document ID的数据
            FireStore.collection("Task")
                .document(taskId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        // 处理错误
                    } else {
                        if (snapshot != null && snapshot.exists()) {
                            assignID = snapshot.getString("assignID")?:""
                            taskTopic = snapshot.getString("taskTopic")?:""
                        }
                    }
                }
            FireStore.collection("User")
                .document(assignId)
                .addSnapshotListener{ snapshot, error ->
                    if (error != null) {
                        // 处理错误
                    } else {
                        if (snapshot != null && snapshot.exists()) {
                            feedbackCount = snapshot.getDouble("feedbackCount")?:0.0
                            starRate = snapshot.getDouble("starRate")?:0.0
                        }
                    }
                }
        }
        val storage = Firebase.storage
        var storageRef = storage.reference
        val avatarImagesRef = storageRef.child("avatar/" + assignID + ".jpg")
        val avatar = remember {
            mutableStateOf<Bitmap?>(null)
        }
        avatarImagesRef.getBytes(2048 * 2048).addOnSuccessListener {
            avatar.value = BitmapFactory.decodeByteArray(it, 0, it.size)
        }.addOnFailureListener {
            // Handle any errors
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(){
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable {navController.popBackStack() }
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Feedback",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier
            .padding(horizontal = 16.dp)
        ){
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
            Text(
                text = assignID,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ){
            Icon(
                imageVector = Icons.Filled.Check,
                "Icon",
                modifier = Modifier
                    .size(30.dp),
                //tint = Color(0xff333333)
            )
            Text(
                text = taskTopic,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Rating",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
                StarRateFeedback()
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Feedback",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0,0)))
        }

        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
        )
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
                    feedbackCount += 1
                    starRate = (starRate*(feedbackCount-1) + newRate)/feedbackCount
                    var feedback = Feedback(
                        feedback = text.text,
                        posterID = user,
                        seekerID = assignID,
                        starRate = starRate
                    )
                    db.collection("User").document(assignID).update("feedbackCount",feedbackCount)
                    db.collection("User").document(assignID).update("starRate",starRate)
                    db.collection("Feedback").document().set(feedback)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Submit", fontSize = 20.sp)
            }
        }
    }
}