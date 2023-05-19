import android.annotation.SuppressLint
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
import com.example.myapplication.MessageListAndInput
import com.example.myapplication.PreviewConversation
import com.example.myapplication.R

data class Chat(
    val name: String,
    val lastMessage: String,
    val time: String,
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun WeChatMainScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Message",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                },
                backgroundColor = Color.White,
                elevation = 2.dp,
            )
        },
    ) {
        var inputText by remember{ mutableStateOf("")}
        val chats = remember {

            mutableListOf(
                Chat("John", inputText, "10:23"),
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp, bottom = 56.dp),
        ) {
            items(chats) { chat ->
                ChatItem(chat, navController)
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, navController: NavController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate("chat/${chat.name}")
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
                text = chat.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chat.lastMessage,
                color = Color.Gray,
                fontSize = 14.sp,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = chat.time,
            color = Color.Gray,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun WeChatChatScreen(chatName: String) {
    Text(
        text = "Chat with $chatName",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    )
    PreviewConversation()
}

/*
@ExperimentalAnimationApi
@Composable
fun WeChatApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") {
            WeChatMainScreen(navController)
        }
        composable(
            "chat/{chatName}",
            arguments = listOf(navArgument("chatName") { type = NavType.StringType }),
        ) { backStackEntry ->
            val chatName = backStackEntry.arguments?.getString("chatName") ?: ""
            WeChatChatScreen(chatName)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PreviewWeChatApp() {
    WeChatApp()
}*/