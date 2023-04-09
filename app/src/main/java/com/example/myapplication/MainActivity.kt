package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = LightColorScheme
            ){
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}


val background = Color (0xFFFFFFFF)
val buttonColor = Color(0xFF0A65FC)
val textFieldColor = Color(0xFFF5F5F5)
val textColor = Color(0xFFFFFFFF)
val cardColor = Color(0xFF0A65FC)
val whiteColor = Color(0xFFFFFFFF)
val LightColorScheme = lightColorScheme (
)
