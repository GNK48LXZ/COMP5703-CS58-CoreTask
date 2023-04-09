package com.example.myapplication
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(
    navController: NavController
){
    val pageState = remember { mutableStateOf(1)}
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
                FindTask()
            }
            if(pageState.value==3){
                MyTask()
            }
            if(pageState.value==4){
                //to do//
            }
            if(pageState.value==5){
                AccountManagement()
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
                onClick = { pageState.value = 1 },
                //selected = true,
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)

            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Check",

                    tint = buttonColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { pageState.value = 2 },
                //selected = false,
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = buttonColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { pageState.value = 3 },
                //selected = false,
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Settings",
                    tint = buttonColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { pageState.value = 4 },
                //selected = true,
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = "Home",
                    tint = buttonColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { pageState.value = 5 },
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "Home",

                    tint = buttonColor
                )
            }
        }
    }
}

@Composable
fun ButtonTest(){
    var selectedItem by remember{ mutableStateOf(0) }
    val items = listOf("Home", "Browser", "My tasks","Message", "Account")

    BottomNavigation {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}
