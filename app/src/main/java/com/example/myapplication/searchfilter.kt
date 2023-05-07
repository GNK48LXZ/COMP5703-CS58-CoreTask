package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Preview
@Composable
fun FilteredUsersScreen() {
    val options1 = listOf("All Task", "Cleaning", "Removals", "Repairs", "Painting")
    var selectedOption1 by remember { mutableStateOf(options1[0]) }
    val options2 = listOf("Bill", "0-50", "51-100", "100-200", "200~")
    var selectedOption2 by remember { mutableStateOf(options2[0]) }
    val options3 = listOf("Status", "Open", "Assigned")
    var selectedOption3 by remember { mutableStateOf(options3[0]) }

    var filterText by remember { mutableStateOf("") }
    val pageState = remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .height(720.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            androidx.compose.material3.Text(
                text = "Browse Tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                fontFamily = Poppins
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                textStyle = MaterialTheme.typography.body2,
                placeholder = { Text("Filter by task name") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search icon",
                        tint = Color.Gray,
                        modifier = Modifier.clickable(onClick = { /* Do something on click */ })
                    )
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterDropdown(
                pageState,
                options = options1,
                selectedOption = selectedOption1,
                onOptionSelected = { option ->
                    selectedOption1 = option
                },
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                pageState,
                options = options2,
                selectedOption = selectedOption2,
                onOptionSelected = { option ->
                    selectedOption2 = option
                },
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                pageState,
                options = options3,
                selectedOption = selectedOption3,
                onOptionSelected = { option ->
                    selectedOption3 = option
                },
                modifier = Modifier.weight(1f)
            )
        }
        Text(text = "."+filterText)
    }
}






