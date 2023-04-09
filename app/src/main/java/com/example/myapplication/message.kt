package com.example.myapplication
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
data class Message(val sender: String, val content: String, val isMe: Boolean)



@Composable
fun MessageListAndInput() {
    var inputText by remember { mutableStateOf("") }
    val messages = remember {
        mutableListOf(
            Message("Lucas", "Hello!", true),
            Message("Sophie", "Hi!", false),
            Message("Lucas", "How are you?", true),
            Message("Sophie", "I'm fine, thank you.", false),
            Message("Lucas", "What are you doing?", true),
            Message("Sophie", "Nothing much, just watching TV.", false),
            Message("Lucas", "OK.", true),
        )
    }
    Column(
        modifier = Modifier.fillMaxSize()
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
                            messages.add(Message("Lucas", inputText, true))
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
                            messages.add(Message("Lucas", inputText, true))
                            inputText = ""
                        }
                    }
                ),
            )
        }
    }
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
                .padding(start = if (message.isMe) 0.dp else 16.dp, end = if (message.isMe) 16.dp else 0.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            androidx.compose.material.Text(
                text = message.content,
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
                    .clickable {/* */ }
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



