package com.example.myapplication

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Dialog(openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "You must enter more than 8 characters.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}

@Composable
fun DateDialog(openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "You must select a date and time for the task.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}

@Composable
fun ClipDialog(openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "Content copied to clipboard.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}

@Composable
fun BudgetDialog(openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "You must enter the budget for this task.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}
@Composable
fun ExceptionDialog(openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = exception)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}

@Composable
fun SignUpDialog(pageState: MutableState<Int>,openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "Sign Up Successful.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        pageState.value = 1
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}
@Composable
fun EmptyDialog(openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "The Email or Password is Empty.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}
@Composable
fun PasswordConfirmDialog(openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "The Password is not Equal.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}
@Composable
fun ForgetPasswordDialog(pageState: MutableState<Int>,openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "Email Already sent")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        pageState.value = 1
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}

@Composable
fun DeleteOfferDialog(offerID:String,openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "Do you want to cancel your offer?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val db = Firebase.firestore
                        db.collection("Offer").document(offerID).delete()
                        openDialog.value = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}
@Composable
fun DeleteTaskDialog(taskID:String,openDialog: MutableState<Boolean>){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "Do you want to cancel your Task?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val db = Firebase.firestore
                        db.collection("Task").document(taskID).delete()
                        openDialog.value = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}
@Composable
fun CompletedDialog(taskId:String,assignId:String,openDialog: MutableState<Boolean>,navController: NavController){
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Reminder")
            },
            text = {
                Text(text = "Are you sure you want to change the status of the task to completed? (This action cannot be undone)")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val db = Firebase.firestore
                        db.collection("Task").document(taskId)
                            .update("status", "Completed")
                        navController.navigate("Feedback/${taskId}/${assignId}")
                        openDialog.value = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}
