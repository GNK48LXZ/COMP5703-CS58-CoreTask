import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.myapplication.*
import com.example.myapplication.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Chat(
    val receiver: String,
    val time: String,
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun WeChatMainScreen(navController: NavController) {

    var receiver by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Message",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                },
                backgroundColor = Color.White,
                elevation = 2.dp,
            )
        }
    ) {

        val chats = remember { mutableStateListOf<Chat>() }
        val db = Firebase.firestore
        val docRef = db.collection("Message")
        LaunchedEffect(Unit) {
            docRef.addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    // 处理错误
                    println("监听集合失败：$exception")
                }
                else{
                    chats.clear()
                    for (documentSnapshot in querySnapshot!!) {
                        // 获取每个文档的数据
                        val documentData = documentSnapshot.data
                        if(documentData.get("sender")== user){
                            receiver = documentData.get("receiver").toString()
                            time = documentData.get("time").toString()
                            if (!chats.any { it.receiver == receiver }) {
                                chats.add(Chat(receiver = receiver, time = time))
                            }
                        }

                    }
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 56.dp),
        ) {
            items(chats.size) { index ->
                ChatItem(chats[index], navController)
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, navController: NavController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate("chat/${chat.receiver}")
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.img1),
            contentDescription = "Profile Image",
            Modifier
                .size(48.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = chat.receiver,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            /*Text(
                text = chat.lastMessage,
                color = Color.Gray,
                fontSize = 14.sp,
            )*/
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = chat.time,
            color = Color.Gray,
            fontSize = 12.sp,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeChatChatScreen(chatName: String, navController: NavController) {
    Text(
        text = "Chat with $chatName",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    )
    PreviewConversation(chatName,navController)
}
