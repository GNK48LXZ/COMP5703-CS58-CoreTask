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
import androidx.compose.ui.unit.TextUnit
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
                FindTask(navController)
            }
            if(pageState.value==2){
                GetItDone(navController)
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
    val selectedPage = pageState.value
    val buttonColor =  Color(0xFF0A65FC)

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
                        tint = if (selectedPage == 1) buttonColor else Color.Gray
                    )
                    Text(
                        "Search",
                        style = MaterialTheme.typography.caption,
                        color = if (selectedPage == 1) buttonColor else Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 2
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
                        painter = painterResource(R.drawable.post_task),
                        contentDescription = "Check",
                        tint = if (selectedPage == 2) buttonColor else Color.Gray,
                        modifier = Modifier
                                .size(24.dp)
                    )
                    Text(
                        "Post",
                        style = MaterialTheme.typography.caption,
                        color = if (selectedPage == 2) buttonColor else Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 3
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
                        Icons.Filled.List,
                        contentDescription = "My Tasks",
                        tint = if (selectedPage == 3) buttonColor else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Tasks",
                        style = MaterialTheme.typography.caption,
                        color = if (selectedPage == 3) buttonColor else Color.Gray,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 4
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
                        painter = painterResource(R.drawable.chat),
                        contentDescription = "Home",
                        tint = if (selectedPage == 4) buttonColor else Color.Gray,
                        modifier = Modifier
                            .size(22.dp)
                    )
                    Text(
                        "Chat",
                        style = MaterialTheme.typography.caption,
                        color = if (selectedPage == 4) buttonColor else Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    pageState.value = 5
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
                        tint = if (selectedPage == 5) buttonColor else Color.Gray,
                    )
                    Text(
                        "Account",
                        style = MaterialTheme.typography.caption,
                        color = if (selectedPage == 5) buttonColor else Color.Gray
                    )
                }
            }
        }
    }
}

