package com.example.myapplication

import NotificationTest
import WeChatChatScreen
import WeChatMainScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.location.FusedLocationProviderClient

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Login"
    ) {
        composable(
            route = "Login"
        ) {
            Login(navController)
        }
        composable(
            route = Screen.GetItDone.route
        ) {
            MainPage(navController)
        }
        composable(
            route = Screen.PostTask.route
        ) {
            PostTaskPage(navController)
        }
        composable(
            route = Screen.ClassificationPage.route
        ) {
            ClassificationPage(navController)
        }
        composable(
            "chat/{receiver}",
            arguments = listOf(navArgument("receiver") { type = NavType.StringType }),
        ) { backStackEntry ->
            val chatName = backStackEntry.arguments?.getString("receiver") ?: ""
            WeChatChatScreen(chatName,navController)
        }
        composable(
            "monitoringDetails/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            MonitoringDetails(taskId, navController)
        }
        composable(
            "EditTask/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            EditTask(taskId, navController)
        }

        composable(
            "MakeAnOffer/{taskId}/{UserID}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType },
                navArgument("UserID") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val UserID = backStackEntry.arguments?.getString("UserID") ?: ""
            MakeAnOffer(taskId,UserID, navController)
        }
        composable(
            "editDate/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            editDate(taskId, navController)
        }
        composable(
            "image"
        ) {
            RequestContentPermission()
        }
        composable(
            "Feedback/{taskId}/{assignId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType },
                navArgument("assignId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            val assignId = backStackEntry.arguments?.getString("assignId") ?: ""

            FeedBack(taskId,assignId,navController)
        }
        composable(
            "OfferDetails/{offerItem.recommendation}/{offerItem.userID}/{taskId}",
            arguments = listOf(
                navArgument("offerItem.recommendation") { type = NavType.StringType },
                navArgument("offerItem.userID") { type = NavType.StringType },
                navArgument("taskId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val recommendation = backStackEntry.arguments?.getString("offerItem.recommendation") ?: ""
            val userID = backStackEntry.arguments?.getString("offerItem.userID") ?: ""
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            OfferDetails(recommendation, userID, taskId, navController)
        }
        composable(
            "test"
        )
        {
            test()
        }
    }


}