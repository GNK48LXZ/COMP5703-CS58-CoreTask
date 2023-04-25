package com.example.myapplication

import WeChatChatScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController:NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.GetItDone.route
    ){
        composable(
            route = "Login"
        ){
            Login(navController)
        }
        composable(
            route = Screen.GetItDone.route
        ){
            MainPage(navController)
        }
        composable(
            route = Screen.PostTask.route
        ){
            PostTaskPage(navController)
        }
        composable(
            "chat/{chatName}",
            arguments = listOf(navArgument("chatName") { type = NavType.StringType }),
        ) { backStackEntry ->
            val chatName = backStackEntry.arguments?.getString("chatName") ?: ""
            WeChatChatScreen(chatName)
        }
        composable(
            "monitoringDetails/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?:""
            MonitoringDetails(taskId,navController)
        }
        composable(
            "MakeAnOffer"
        ){
            MakeAnOffer(navController)
        }
        composable(
            "SubmitInf"
        ){
            SubmitInf()
        }
        composable(
            "image"
        ){
            RequestContentPermission()
        }
    }
}