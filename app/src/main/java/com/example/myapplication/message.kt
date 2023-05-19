package com.example.myapplication
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
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

val db = Firebase.firestore
data class Message(
    val sender: String?= null,
    val inputText: String? = null,
    val isMe: Boolean,
    val reciever: String?= null,
    val time: String? = null)
@Composable
fun MessageListAndInput() {
    var inputText by remember { mutableStateOf("") }
    // When the user sends a new message
    var sender by remember { mutableStateOf("") }
    var reciever by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val storage = Firebase.storage
    var storageRef = storage.reference


// 获取用户头像
    val avatarImagesRef = storageRef.child("avatar/"+user+".jpg")
    val avatar =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    avatarImagesRef.getBytes(2048*2048).addOnSuccessListener {
        avatar.value = BitmapFactory.decodeByteArray(it,0,it.size)
    }.addOnFailureListener {
        // 处理任何错误
    }
    sender = auth.currentUser?.email.toString()



// 监听指定Document ID的数据
    LaunchedEffect(Unit) {
        FireStore.collection("Message")
            .document(sender.toString())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // 处理错误
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        sender = (snapshot.getString("sender")?:"")
                        inputText = snapshot.getString("Inputext")?:""
                        reciever = snapshot.getString("reciever")?:""
                        time = snapshot.getString("time")?:""

                    }
                }
            }
    }

    val messages = remember {
        mutableListOf(
            Message("Lucas", "Hello!", true, "Sophie","12:00"),
            Message("Sophie", "Hi!", false, "Lucas", "12:00"),
        )
    }
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(messages.size) { index ->
                MessageItem(messages[index])
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = inputText,
                onValueChange = {
                    inputText = it
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
                        if (inputText.isNotEmpty()) {
                            messages.add(Message("Lucas", inputText, true, "Sophie","12:00"))
                            inputText = ""
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
                            messages.add(Message("Lucas", inputText, true, "Sophie","12:00"))
                            inputText = ""
                        }
                    }
                )
            )
//            androidx.compose.material3.IconButton(
//                onClick = {
//
//                },
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Row {
//                    Icon(Icons.Filled.Settings, contentDescription = "设置")
//                }
            }
        }
     //   db.collection("Message").document("document.id").set(Message)
    }


@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = if (message.isMe) Arrangement.End else Arrangement.Start,
    ) {
        if (!message.isMe) {
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
                    start = if (message.isMe) 0.dp else 16.dp,
                    end = if (message.isMe) 16.dp else 0.dp
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            message.inputText?.let {
                androidx.compose.material.Text(
                    text = it,
                    style = MaterialTheme.typography.body1,
                    color = if (message.isMe) Color.White else Color.Black,
                    modifier = Modifier
                        .background(
                            if (message.isMe) Color.Blue else Color.Gray,
                            MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)
                        .align(if (message.isMe) Alignment.End else Alignment.Start),
                )
            }
        }
        if (message.isMe) {
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewConversation() {
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
                    .clickable {}
                    .padding(horizontal = 16.dp)
                    .size(30.dp),
                tint = Color(0xff333333)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "John",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        MessageListAndInput()
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


