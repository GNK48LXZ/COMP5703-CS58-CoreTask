package com.example.myapplication

import No
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun GetItDone(navController: NavController) {
    //监听
    val db = Firebase.firestore
    var notice by remember {
        mutableStateOf(false)
    }
    notice = Listen()
    if(notice==true){
        No()
        db.collection("User").document(user).update("notice",false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(720.dp)
            .background(background)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Text(
            text = "Let's get your things done!",
            fontSize = 28.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 30.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            /*androidx.compose.material.Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp),
                onClick = { navController.navigate(Screen.ClassificationPage.route) },
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                androidx.compose.material.Text(
                    text = "I am a Task Poster",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }*/
            androidx.compose.material.Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp),
                onClick = { page.value=2 },
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                androidx.compose.material.Text(
                    text = "I'll take on Tasks",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        /*Text(
            text = "OR choose your task category to create your task.",
            fontSize = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = buttonColor,
            fontWeight = FontWeight.W500,
        )
        Spacer(modifier = Modifier.height(10.dp))*/
        Text(
            text = "Choose one category and post your Task!",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 40.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Card(
                modifier = Modifier
                    .size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { navController.navigate(route = Screen.PostTask.route) },
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cleaning),
                        tint = whiteColor,
                        contentDescription = "the cleaning"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor,
                        text = "Cleaning",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */ },
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.removals),
                        tint = whiteColor,
                        contentDescription = "the removal"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor,
                        text = "Removals",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(22.dp))
        Row {
            Card(
                modifier = Modifier
                    .size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */ },
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.build),
                        contentDescription = "the repairs",
                        tint = whiteColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor,
                        text = "Repairs",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */ },
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.painting),
                        tint = whiteColor,
                        contentDescription = "the painting"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor,
                        text = "Painting",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(22.dp))
        Row {
            androidx.compose.material3.Card(
                modifier = Modifier
                    .size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */ },
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.others),
                        contentDescription = "the repairs",
                        tint = whiteColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor,
                        //text = "Others",
                        text = "Other",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}





