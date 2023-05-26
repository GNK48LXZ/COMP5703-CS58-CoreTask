package com.example.myapplication
import Chat
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val db = Firebase.firestore
data class Message(
    val sender: String?= null,
    val inputText: String? = null,
    val isMe: Boolean,
    val reciever: String?= null,
    val time: String? = null)
data class Messages(
    val sender: String?= null,
    val inputText: String? = null,
    val receiver: String?= null,
    val time: String? = null
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageListAndInput(chatName: String?) {
    var inputText by remember { mutableStateOf("") }
    // When the user sends a new message
    var sender by remember { mutableStateOf("") }
    var receiver by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var inputText2 by remember { mutableStateOf("") }

    //val storage = Firebase.storage
    //var storageRef = storage.reference


// 获取用户头像
    /*val avatarImagesRef = storageRef.child("avatar/"+user+".jpg")
    val avatar =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
        avatar.value = BitmapFactory.decodeByteArray(it,0,it.size)
    }.addOnFailureListener {
        // 处理任何错误
    }*/
    sender = user

    //val messages = remember {
        //mutableListOf(
            //Messages(user,"Hello","choice@gmail.com","11:00 AM")
            //Message("Lucas", "Hello!", true, "Sophie","12:00"),
            //Message("Sophie", "Hi!", false, "Lucas", "12:00"),
        //)
    //}
    var messages = remember { mutableStateListOf<Messages>() }
    val db = Firebase.firestore
    val docRef = db.collection("Message")
    LaunchedEffect(Unit) {
        docRef.addSnapshotListener { querySnapshot, exception ->
            if (exception != null) {
                // 处理错误
                println("监听集合失败：$exception")
            }
            else{
                messages.clear()
                for (documentSnapshot in querySnapshot!!) {
                    // 获取每个文档的数据
                    val documentData = documentSnapshot.data
                    if(
                        (documentData.get("sender") == user && documentData.get("receiver") == chatName)
                        ||(documentData.get("sender") == chatName && documentData.get("receiver") == user)
                    ){
                        inputText = documentData.get("inputText").toString()
                        sender = documentData.get("sender").toString()
                        receiver = documentData.get("receiver").toString()
                        time = documentData.get("time").toString()
                        messages.add(Messages(sender = sender,receiver = receiver, inputText = inputText, time = time))
                        messages.sortedBy { it.time }
                    }

                }
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            val sortedMessages = messages.sortedBy { it.time }
            items(sortedMessages.size) { index ->
                MessageItem(sortedMessages[index])
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = inputText2,
                onValueChange = {
                    inputText2 = it
                },
                modifier = Modifier.weight(1f),

                singleLine = true,
                placeholder = {
                    androidx.compose.material.Text(
                        text = "Type a message...",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray,
                    )
                },

                textStyle = MaterialTheme.typography.body1,
                trailingIcon = {
                    IconButton(onClick = {
                        if (inputText2.isNotEmpty()) {

                        }
                    }) {


                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send,
                    keyboardType = KeyboardType.Text,
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (inputText.isNotEmpty()) {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val timeStamp = LocalDateTime.now().format(formatter)
                            var m = Messages(sender = user,receiver = chatName, inputText = inputText2, time = timeStamp)
                            val db2 = Firebase.firestore
                            db2.collection("Message").document().set(m)
                            inputText2 = ""
                        }
                    }
                )
            )
            }
        }
     //   db.collection("Message").document("document.id").set(Message)
    }


@Composable
fun MessageItem(message: Messages) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = if (message.sender==user) Arrangement.End else Arrangement.Start,
    ) {
        if (message.sender!=user) {
            Image(
                painter = painterResource(R.drawable.img1),
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp),
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = if (message.sender==user) 0.dp else 16.dp,
                    end = if (message.sender==user) 16.dp else 0.dp
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            message.inputText?.let {
                androidx.compose.material.Text(
                    text = it,
                    style = MaterialTheme.typography.body1,
                    color = if (message.sender==user) Color.White else Color.Black,
                    modifier = Modifier
                        .background(
                            if (message.sender==user) buttonColor else textFieldColor,
                            MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)
                        .align(if (message.sender==user) Alignment.End else Alignment.Start),
                )
            }
        }
        if (message.sender==user) {
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewConversation(chatName: String?, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBack,
                "Icon",
                modifier = Modifier
                    .clickable {navController.popBackStack()}
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = chatName.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        MessageListAndInput(chatName)
        Spacer(modifier = Modifier.height(380.dp))
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 0)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
        )

        Spacer(modifier = Modifier.height(30.dp))

    }
}


