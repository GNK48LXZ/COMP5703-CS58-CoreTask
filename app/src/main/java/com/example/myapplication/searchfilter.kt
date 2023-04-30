package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Preview
@Composable
fun FilteredUsersScreen() {
    var filterText by remember { mutableStateOf("") }
    var filteredTasks by remember { mutableStateOf(emptyList<DocumentSnapshot>()) }

    Row {
        TextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filter by task name") },
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                Firebase.firestore.collection("Task")
                    .get()
                    .addOnSuccessListener { result ->
                        filteredTasks = result.documents.filter { doc ->
                            doc.getString("name")?.contains(filterText, ignoreCase = true) ?: false
                        }
                    }
                    .addOnFailureListener { exception ->
                        // 处理异常
                    }
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text("Apply filter")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}



