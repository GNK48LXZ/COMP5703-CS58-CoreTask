package com.example.myapplication

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

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