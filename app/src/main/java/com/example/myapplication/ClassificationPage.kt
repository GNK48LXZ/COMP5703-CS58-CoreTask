package com.example.myapplication

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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.Screen
import com.example.myapplication.background
import com.example.myapplication.buttonColor
import com.example.myapplication.cardColor
import com.example.myapplication.page

@Composable
fun ClassificationPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(background)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            androidx.compose.material.Icon(
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
                text = "Chose a category",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(
            modifier = Modifier.height(20.dp)
        )
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
                        text = "Others",
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        }
    }
}





