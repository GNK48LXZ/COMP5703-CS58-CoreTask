package com.example.myapplication
//import WeChatApp
import WeChatMainScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

val page = mutableStateOf(1)
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(
    navController: NavController,
){
    val pageState = page
    Scaffold(
        bottomBar ={
            BottomToolbar(pageState = pageState)
        }
    ){
        Column(
        ) {
            if(pageState.value==1){
                GetItDone(navController)
            }
            if(pageState.value==2){
                FindTask(navController)
            }
            if(pageState.value==3){
                MyTask(navController)
            }
            if(pageState.value==4){
                WeChatMainScreen(navController)
            }
            if(pageState.value==5){
                AccountManagement(navController)
            }
        }
        Column(
            modifier = Modifier.padding(it),
        ) {
        }
    }

}

@Composable
fun BottomToolbar(pageState: MutableState<Int>) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            IconButton(
                onClick = {
                    pageState.value = 1
                    page.value = 1
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Check",
                        tint = buttonColor
                    )
                    Text(
                        "Home",
                        style = MaterialTheme.typography.caption,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 2
                    page.value = 2
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = buttonColor
                    )
                    Text(
                        "Search",
                        style = MaterialTheme.typography.caption,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 3
                    page.value = 3
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = buttonColor
                    )
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.caption,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 4
                    page.value = 4
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = "Home",
                        tint = buttonColor
                    )
                    Text(
                        "Chat",
                        style = MaterialTheme.typography.caption,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 5
                    page.value = 5
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Home",
                        tint = buttonColor
                    )
                    Text(
                        "Account",
                        style = MaterialTheme.typography.caption,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
