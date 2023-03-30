package com.example.myapplication
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainPage(){
    TopBar()
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

@Composable
fun TopBar(){
    Scaffold(
        topBar = {
            TopAppBar {
                TopAppBar(
                    title = { Text("Simple TopAppBar") },
                    navigationIcon = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        // RowScope here, so these icons will be placed horizontally
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(Icons.Filled.Home, contentDescription = "Localized description")
                        }
                    }
                )
            }
        },
        bottomBar = {
            ButtonTest()
        }
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Task(modifier = Modifier
            .padding(it)
            .padding(8.dp))
    }
}
@Composable
fun Task(modifier: Modifier){
    Card(
        backgroundColor = Color.Gray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column() {
            Row(){
                Text("Remove tree roots,flatten ground")
                Text("AU$100")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column() {
                Text("Vaucluse NSW 2030, Australia")
                Text("Fri,Mar 25")
                Text("Any Time")
                Text("Open")
            }
        }
    }

}

@Composable
fun ArtistCard(modifier: Modifier) {
    Row(
        modifier = Modifier.padding(top = 20.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Column(modifier=Modifier.fillMaxWidth()){
            Text("Alfred Sisley")
            Text("3 minutes ago")
            Image(painter = painterResource(id = R.drawable.task1),
                modifier = Modifier.padding(end = 30.dp),
                contentDescription = null)
        }
    }

}

@Composable
fun MyApp() {
    MaterialTheme {
        Button(
            onClick = { /* ... */ },
            // Uses ButtonDefaults.ContentPadding by default
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            )
        ) {
            // Inner content including an icon and a text label
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Like")
        }
    }
}