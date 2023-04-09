package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController:NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.GetItDone.route
    ){
        composable(
            route = Screen.GetItDone.route
        ){
            MainPage(navController)
        }
        composable(
            route = Screen.PostTask.route
        ){
            PostTaskPage()
        }
    }
}