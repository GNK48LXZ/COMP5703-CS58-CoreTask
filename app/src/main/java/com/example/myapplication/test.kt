package com.example.myapplication

import androidx.compose.runtime.Composable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun test(){
    val db = Firebase.firestore

    val docRef = db.collection("Message")
    docRef.addSnapshotListener { querySnapshot, exception ->
        if (exception != null) {
            // 处理错误
            println("监听集合失败：$exception")
        }
        for (documentSnapshot in querySnapshot!!) {
            // 获取每个文档的数据
            val documentData = documentSnapshot.data
            var a = documentData.get("inputText")
            // 处理文档数据
            // ...

            // 示例：打印文档的内容
            println(a)
        }
    }
}