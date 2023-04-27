package com.example.myapplication


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.Purple200
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

val auth = Firebase.auth

@Composable
fun Login(navController: NavController) {
    val pageState = remember {
        mutableStateOf(1)
    }
    if (pageState.value == 1) {
        LoginScreen(pageState, navController)
    } else if (pageState.value == 2) {
        SignUpScreen(pageState)
    } else if (pageState.value == 3) {
        ForgotPassword(pageState)
    }
}

var user = "lxz@test.com"
var exception = ""

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(pageState: MutableState<Int>, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val openDialog = remember { mutableStateOf(false) }
    ExceptionDialog(openDialog)
    val openDialog_empty = remember { mutableStateOf(false) }
    EmptyDialog(openDialog_empty)

    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        androidx.compose.material3.Text(
            text = "Login to Your Account!",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(60.dp))
        androidx.compose.material3.OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { androidx.compose.material3.Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        androidx.compose.material3.OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { androidx.compose.material3.Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.TextButton(
            onClick = { pageState.value = 3 },
            colors = androidx.compose.material3.ButtonDefaults.textButtonColors(contentColor = Color.Blue),
            modifier = Modifier.align(Alignment.End)
        ) {
            androidx.compose.material3.Text(text = "Forgot Password")
        }
        androidx.compose.material3.Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    openDialog_empty.value = true
                } else {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success")

                                user = auth.currentUser?.email.toString()
                                navController.navigate(route = Screen.GetItDone.route) {
                                    popUpTo("Login") {
                                        inclusive = true
                                    }
                                }
//                            AccountMain(pageState = mutableStateOf(1))
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                exception = task.exception.toString().substringAfter(": ")
                                openDialog.value = true
                            }
                        }
                }

            },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(25.dp))
        androidx.compose.material3.Text(
            text = "Don't have an account?",
            fontWeight = FontWeight.Thin,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        androidx.compose.material3.TextButton(
            onClick = {
                pageState.value = 2
            },
            colors = androidx.compose.material3.ButtonDefaults.textButtonColors(contentColor = Color.Blue),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            androidx.compose.material3.Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.height(20.dp))
        androidx.compose.material3.Button(
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(2.dp, color = Color.Black),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.White),
            onClick = { /*TODO*/ }
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.google),
                //painter = painterResource(id = R.drawable.google),
                contentDescription = null
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            androidx.compose.material3.Text(
                "Login with Google",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        androidx.compose.material3.Button(
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(2.dp, color = Color.Black),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.White),
            onClick = { /*TODO*/ }
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.facebook),
                //painter = painterResource(id = R.drawable.google),
                contentDescription = null
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            androidx.compose.material3.Text(
                "Login with Facebook",
                color = Color.Black
            )
        }
    }
}

data class UserProfile(
    val id: String? = null,
    val name: String? = null,
    val introduction: String? = null,
    val location: String? = null,
    val skill: String? = null,
    val certificate: String? = null,
    val starRate: String? = null,
)

@Composable
fun SignUpScreen(pageState: MutableState<Int>) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password_confirm by remember { mutableStateOf("") }

    val openDialog = remember { mutableStateOf(false) }
    SignUpDialog(pageState,openDialog)
    val openDialog_empty = remember { mutableStateOf(false) }
    EmptyDialog(openDialog_empty)
    val openDialog_confirm = remember { mutableStateOf(false) }
    PasswordConfirmDialog(openDialog_confirm)
    val Dialog = remember { mutableStateOf(false) }
    Dialog(Dialog)
    val openDialog_exception = remember { mutableStateOf(false) }
    ExceptionDialog(openDialog_exception)

    Row() {
        androidx.compose.material.Icon(
            imageVector = Icons.Filled.ArrowBack,
            "Icon",
            modifier = Modifier
                .clickable { pageState.value = 1 }
                .padding(vertical = 30.dp)
                .padding(horizontal = 10.dp)
                .size(30.dp),
            tint = Color(0xff333333)
        )
        Spacer(modifier = Modifier.width(5.dp))
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Create an account",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password_confirm,
            onValueChange = { password_confirm = it },
            label = { Text("Password Confirm") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    openDialog_empty.value = true
                } else if (!password_confirm.equals(password)) {
                    openDialog_confirm.value = true
                } else if (password.length < 8) {
                    Dialog.value = true
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                                val userId = auth.currentUser?.email.toString()
                                user = auth.currentUser?.email.toString()
                                Log.d(ContentValues.TAG, "XXXXXXXXXXXXXXXXXX")
                                openDialog.value = true
                                val db = Firebase.firestore
                                val userProfile = UserProfile(id = userId)
                                db.collection("User").document(userId).set(userProfile)
                                //转到用户界面
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(
                                    ContentValues.TAG,
                                    "createUserWithEmail:failure",
                                    task.exception
                                )
                                exception = task.exception.toString().substringAfter(": ")
                                openDialog_exception.value = true
                            }
                        }
                }

            },
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign up")
        }
    }
}

@Composable
fun ForgotPassword(pageState: MutableState<Int>) {

    var email by remember { mutableStateOf("") }

    val openDialog_forget = remember { mutableStateOf(false) }
    ForgetPasswordDialog(pageState,openDialog_forget)
    val openDialog_exception = remember { mutableStateOf(false) }
    ExceptionDialog(openDialog_exception)
    val openDialog_empty = remember { mutableStateOf(false) }
    EmptyDialog(openDialog_empty)

    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Forgot Your Password?",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Enter your email address and we’ll send\n" +
                    "you a link to reset your password.",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Thin,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (email.isEmpty()) {
                    openDialog_empty.value = true
                } else {
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                openDialog_forget.value = true
                                // Password reset email sent successfully
                                // Show a success message to the user
                                // Redirect user to login screen or home screen

                            } else {
                                // Password reset email failed to send
                                // Show an error message to the user
                                exception = task.exception.toString().substringAfter(": ")
                                openDialog_exception.value = true
                            }
                        }
                }
            },
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sent Email")
        }
        Spacer(modifier = Modifier.height(32.dp))
        TextButton(
            onClick = { pageState.value = 1 },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Back to Login")
        }
    }
}


