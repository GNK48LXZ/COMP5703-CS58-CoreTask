package com.example.myapplication

import androidx.compose.animation.AnimatedVisibility

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Purple200
import kotlinx.coroutines.selects.select

@Composable
fun AccountManagement() {
    val pageState = remember {
        mutableStateOf(1)
    }
    if (pageState.value == 1) {
        AccountMain(pageState, user)
    } else if (pageState.value == 2) {
        Settings(pageState)
    } else if (pageState.value == 3) {
        Account(pageState)
    }


}
@Composable
fun AccountMain(pageState: MutableState<Int>,user : String){
    Box(
        modifier = Modifier
            .padding(top = 16.dp, start = 350.dp)
            .size(24.dp)
    ) {
        IconButton(
            onClick = { pageState.value = 2 },
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(Icons.Filled.Settings, contentDescription = "设置")
        }
    }

    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 50.dp)
                .height(100.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { pageState.value = 3 },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Icon(Icons.Filled.Person,modifier = Modifier.size(70.dp), contentDescription = "person")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Spacer(modifier = Modifier.height(10.dp))
                androidx.compose.material3.Text(
                    text = user,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.width(50.dp))
                Spacer(modifier = Modifier.height(10.dp))
                androidx.compose.material3.Text(
                    text = "Last Online",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                androidx.compose.material3.Text(
                    text = "Member Since",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }

        }

        Spacer(modifier = Modifier.height(30.dp))
        Row() {
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(buttonColor),
                onClick = {
                }
            ) {
                androidx.compose.material3.Text("Tasker", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(buttonColor),
                onClick = {
                }
            ) {
                androidx.compose.material3.Text("Poster", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material3.Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            text = "Location",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            lineHeight = 20.sp,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            text = "Introduction",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            lineHeight = 20.sp,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            text = "Skills",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            lineHeight = 20.sp,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            text = "Certificate",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            lineHeight = 20.sp,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            text = "FeedBack",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            lineHeight = 20.sp,
            fontSize = 20.sp
        )
    }
}


@Composable
fun Settings(pageState: MutableState<Int>){

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Setting") },
                navigationIcon = {
                    IconButton(onClick = { pageState.value =1 }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                backgroundColor = background,
                contentColor = Color.Black,
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SettingItem(title = "Payment Method", icon = Icons.Outlined.ShoppingCart, onClick = {})
            Divider()
            SettingItem(title = "Change Password", icon = Icons.Outlined.Lock, onClick = {})
            Divider()
            SettingItem(title = "Notification preferences", icon = Icons.Outlined.Notifications, onClick = {})
            Divider()
            SettingItem(title = "Task alert setting", icon = Icons.Outlined.Settings, onClick = {})
            Divider()
            SettingItem(title = "Historical billings", icon = Icons.Outlined.Star, onClick = {})
            Divider()
            SettingItem(title = "General", icon = Icons.Outlined.Settings, onClick = {})
            Divider()
            SettingItem(title = "Guidelines", icon = Icons.Outlined.Info, onClick = {})
            Divider()
            SettingItem(title = "Contact Us", icon = Icons.Outlined.Info, onClick = {})
            Divider()
            SettingItem(title = "Insurance protection", icon = Icons.Outlined.Info, onClick = {})
            Divider()
            SettingItem(title = "Legal", icon = Icons.Outlined.Info, onClick = {})
            Divider()
            SettingItem(title = "Logout", icon = Icons.Outlined.Info, onClick = {})
        }
    }
}

@Composable
fun SettingItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
        }
    }
}
@Composable
fun Account(pageState: MutableState<Int>){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit Profile") },
                backgroundColor = background,
                contentColor = Color.Black,
                elevation = AppBarDefaults.TopAppBarElevation,
                navigationIcon = {
                    IconButton(onClick = { pageState.value = 1 }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileItem(title = "Name", content = "Jetpack Compose")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ProfileItem(title = "Location", content = "18 岁")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ProfileItem(title = "Introduction", content = "男")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ProfileItem(title = "Email", content = "123456789")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ProfileItem(title = "Birth", content = "123456789")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ProfileItem(title = "Skills", content = "123456789")
        }
    }
}
@Composable
fun ProfileItem(title: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = content, fontWeight = FontWeight.Normal, fontSize = 16.sp)
    }
}
