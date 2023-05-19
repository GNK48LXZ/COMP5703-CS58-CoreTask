package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class Offer(
    val recommendation: String? = null,
    val userID: String? = null,
    val taskID: String? = null,
    val status: String = "Pending"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MakeAnOffer(taskId: String,UserID:String,navController: NavController) {
    val recommendation = remember { mutableStateOf("") }
    val userID = remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()

    val offer = Offer(
        recommendation.value,
        userID.value,
        taskId
    )
    userID.value = auth.currentUser?.email.toString()
    MaterialTheme(colorScheme = LightColorScheme) {
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
                    text = "Post your information",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "Certificate",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 30.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = " (Required)",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(textFieldColor),
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 100.dp, height = 100.dp)
            ) {
                androidx.compose.material3.Icon(
                    painter = painterResource(R.drawable.photo),
                    tint = buttonColor,
                    contentDescription = "add certificate",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "Self recommendation",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 30.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = " (Optional)",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            TextField(
                value = recommendation.value,
                onValueChange = { recommendation.value = it },
                modifier = Modifier
                    .padding(24.dp)
                    .width(340.dp)
                    .height(180.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val db = Firebase.firestore
                        db.collection("Offer").document().set(offer)
                        db.collection("User").document(UserID).update("notice",true)
                        //navController.navigate("SubmitInf/${taskId}")
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(buttonColor)
                ) {
                    Text(text = "Apply", fontSize = 20.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitInf(navController: NavController) {
    MaterialTheme(colorScheme = LightColorScheme) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(background)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(modifier = Modifier.height(150.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material3.Icon(
                    painter = painterResource(R.drawable.check),
                    tint = buttonColor,
                    contentDescription = "successfully",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
                Text(
                    text = "Task created successfully!",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 25.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Please be patient and wait for the reply",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 25.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        //navController.navigate("monitoringDetails/${taskId}")
                        //navController.navigate(route = "monitoringDetails/${taskId}") {
                        //    popUpTo(Screen.GetItDone.route) {
                        //        inclusive = true
                        //    }
                        //}

                        val db = Firebase.firestore
                        val collectionRef = db.collection("Task")
                        val query = collectionRef.whereEqualTo("userID", user)
                        query.addSnapshotListener { snapshot, e ->
                            if (e != null) {
                                return@addSnapshotListener
                            }

                            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                                val documentId = snapshot.documents[0].id
                                navController.navigate("monitoringDetails/${documentId}")
                            } else {
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(buttonColor)
                ) {
                    Text(text = "Back to home page", fontSize = 20.sp)
                }
            }
        }
    }
}




