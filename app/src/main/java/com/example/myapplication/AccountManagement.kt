package com.example.myapplication

import No
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

@Composable
fun AccountManagement(
    navController: NavController
) {

    val db = Firebase.firestore
    var notice by remember {
        mutableStateOf(false)
    }
    notice = Listen()
    if(notice==true){
        No()
        db.collection("User").document(user).update("notice",false)
    }

    val pageState = remember {
        mutableStateOf(1)
    }
    if (pageState.value == 1) {
        AccountMain(pageState)
    } else if (pageState.value == 2) {
        Settings(pageState,navController)
    } else if (pageState.value == 3) {
        Account(pageState)
    } else if (pageState.value == 4){
        EditUserProfile(pageState)
    }
}
@Composable
fun AccountMain(pageState: MutableState<Int>){
    var certificate by remember { mutableStateOf("") }
    var introduction by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var skill by remember { mutableStateOf("") }

    val storage = Firebase.storage
    var storageRef = storage.reference
    val avatarImagesRef = storageRef.child("avatar/"+user+".jpg")
    val avatar =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
        avatar.value = BitmapFactory.decodeByteArray(it,0,it.size)
    }.addOnFailureListener {
        // Handle any errors
    }

    LaunchedEffect(Unit) {
        // 监听指定Document ID的数据
        FireStore.collection("User")
            .document(user)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // 处理错误
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        certificate = snapshot.getString("certificate")?:""
                        introduction = snapshot.getString("introduction")?:""
                        location = snapshot.getString("location")?:""
                        name = snapshot.getString("name")?:""
                        skill = snapshot.getString("skill")?:""
                    }
                }
            }
    }
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
                .padding(top = 30.dp)
                .height(100.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { /*pageState.value = 3*/ },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(80.dp)
            ) {
                avatar.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                if(user.length<14){
                    Text(
                        text = user,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
                else{
                    Text(
                        text = user,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
                Spacer(modifier = Modifier.width(50.dp))
                Spacer(modifier = Modifier.height(10.dp))
                StarRate()
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
                Text("Tasker", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(buttonColor),
                onClick = {
                }
            ) {
                Text("Poster", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 15.dp),
            text = "Introduction",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            introduction,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 15.dp),
            text = "Address",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            location,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 15.dp),
            text = "Skills",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            skill,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 15.dp),
            text = "Certificate",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(pageState: MutableState<Int>,navController: NavController){

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(background),
                title = { Text(text = "Setting") },
                navigationIcon = {
                    IconButton(onClick = { pageState.value =1 }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(720.dp)
                .padding(innerPadding)
                .background(background)
                .verticalScroll(rememberScrollState())
        ) {
            SettingItem(title = "Payment Method", icon = Icons.Outlined.ShoppingCart, onClick = {})
            Divider()
            SettingItem(title = "Change Password", icon = Icons.Outlined.Lock, onClick = {})
            Divider()
            SettingItem(
                title = "Edit My Information",
                icon = Icons.Outlined.Edit,
                onClick = {
                    pageState.value = 4
                }
            )
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
            SettingItem(
                title = "Logout", 
                icon = Icons.Outlined.Info, 
                onClick = {
                    user = ""
                    navController.navigate(route = "Login"){
                        popUpTo(Screen.GetItDone.route){
                            inclusive = true
                        }
                    }
                    page.value = 1
                }
            )
        }
    }
}

@Composable
fun SettingItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        //elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(background)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp)
                .background(background)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(pageState: MutableState<Int>){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit Profile") },
                //backgroundColor = background,
                //contentColor = Color.Black,
                //elevation = AppBarDefaults.TopAppBarElevation,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserProfile(pageState: MutableState<Int>){
    var certificate by remember { mutableStateOf("") }
    var introduction by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var skill by remember { mutableStateOf("") }

    val storage = Firebase.storage
    var storageRef = storage.reference
    val avatarImagesRef = storageRef.child("avatar/"+user+".jpg")

    LaunchedEffect(Unit) {
        // 监听指定Document ID的数据
        FireStore.collection("User")
            .document(user)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // 处理错误
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        certificate = snapshot.getString("certificate")?:""
                        introduction = snapshot.getString("introduction")?:""
                        location = snapshot.getString("location")?:""
                        name = snapshot.getString("name")?:""
                        skill = snapshot.getString("skill")?:""
                    }
                }
            }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit My Information") },
                navigationIcon = {
                    IconButton(onClick = { pageState.value = 1 }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(720.dp)
                .background(background)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            val bitmap = RequestContentPermission()
            val baos = ByteArrayOutputStream()
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            }
            val data = baos.toByteArray()
            Text(
                "Name",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            Text(
                "Introduction",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = introduction,
                onValueChange = { introduction = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            Text(
                "Address",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            Text(
                "Certificate",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            ElevatedButton(
                onClick = { /* Do something! */ },
                modifier = Modifier
                    .padding(16.dp)
                    .height(110.dp)
                    .width(160.dp),
                colors = ButtonDefaults.buttonColors(textFieldColor)
            ) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(50.dp)
                )
            }
            Text(
                "Skill",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                value = skill,
                onValueChange = { skill = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    val db = Firebase.firestore
                    val updateUserProfile = db.collection("User").document(user)
                    updateUserProfile.update("name",name)
                    updateUserProfile.update("introduction",introduction)
                    updateUserProfile.update("location",location)
                    updateUserProfile.update("skill",skill)
                    pageState.value = 1
                    if(bitmap!=null)
                        avatarImagesRef.putBytes(data)
                },
                colors = ButtonDefaults.buttonColors(buttonColor)
            ) {
                Text("Save", fontSize = 20.sp)
            }
        }
    }
}
