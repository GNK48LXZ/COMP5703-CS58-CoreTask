package com.example.myapplication

import android.graphics.Color
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun GetItDone(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(720.dp)
            .background(background)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Text(
            text = "Let's get your things done!",
            fontSize = 38.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 40.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Post a task. Receive offers. Get it done.",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 40.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.padding(start = 16.dp).size(width = 250.dp, height = 64.dp)
        ) {
            Row {
                Text(text = "Post a task",
                    fontSize = 28.sp,
                    modifier = Modifier.padding(end = 10.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow Forward",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Top trending categories",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 40.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row{
            Card(
            modifier = Modifier.size(width = 200.dp, height = 150.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {navController.navigate(route = Screen.PostTask.route)},
            colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center) {
                    Icon(
                        painter = painterResource(R.drawable.cleaning),
                        tint = whiteColor,
                        contentDescription = "the cleaning"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color= textColor,
                        text = "Cleaning",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
            Card(
                modifier = Modifier.size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */},
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center) {
                    Icon(
                        painter = painterResource(R.drawable.removals),
                        tint = whiteColor,
                        contentDescription = "the removal"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color= textColor,
                        text = "Removals",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(22.dp))
        Row{
            Card(
                modifier = Modifier.size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */},
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center) {
                    Icon(
                        painter = painterResource(R.drawable.build),
                        contentDescription = "the repairs",
                        tint = whiteColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color= textColor,
                        text = "Repairs",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
            Card(
                modifier = Modifier.size(width = 200.dp, height = 150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {/* 点击事件 */},
                colors = CardDefaults.cardColors(cardColor)
            ) {
                Column(Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center) {
                    Icon(
                        painter = painterResource(R.drawable.painting),
                        tint = whiteColor,
                        contentDescription = "the painting"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                        color= textColor,
                        text = "Painting",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
        Text("35435345")
        Text("5345345")
        Text("5345345")
    }
}





