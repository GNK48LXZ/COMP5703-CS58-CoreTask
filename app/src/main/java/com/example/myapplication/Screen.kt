package com.example.myapplication

sealed class Screen(val route:String){
    object GetItDone: Screen(route = "GetItDone_Screen")
    object PostTask: Screen(route = "PostTask_Screen")
}
