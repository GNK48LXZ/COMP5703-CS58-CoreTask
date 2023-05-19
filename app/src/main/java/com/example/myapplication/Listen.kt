package com.example.myapplication

import androidx.compose.runtime.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Listen():Boolean{
    val db = Firebase.firestore
    var notice by remember {
        mutableStateOf(false)
    }

    val docRef = db.collection("User").document(user)
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            //error
        }
        if (snapshot != null && snapshot.exists()) {
            notice = snapshot.getBoolean("notice") == true
        } else {
            //error
        }
    }
    return notice
}