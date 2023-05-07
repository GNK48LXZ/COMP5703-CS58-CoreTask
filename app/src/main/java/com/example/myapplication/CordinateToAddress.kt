package com.example.myapplication

import android.location.Geocoder
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext


@Composable
fun getCurrentLocation(la: Double, lo: Double):String{
    val g = Geocoder(LocalContext.current)
    val l = g.getFromLocation(la,lo,1)
    var currentLocation : String = ""
    if (l != null) {
        if(l.size>0)
            currentLocation = l[0].getAddressLine(0)
    }
    return currentLocation
}
