package com.example.myapplication


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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
        LoginScreen(pageState,navController)
    } else if (pageState.value == 2) {
        SignUpScreen(pageState)
    } else if (pageState.value == 3) {
        ForgotPassword(pageState)
    }
}

var user = "lxz@test.com"


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(pageState: MutableState<Int>,navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    // 用于在屏幕上显示错误消息
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(60F)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Login to Your Account!",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(25.dp))
                // 添加输入框，让用户输入电子邮件和密码
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email address") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        visualTransformation = PasswordVisualTransformation(),
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    TextButton(
                        onClick = { pageState.value = 3 },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue),
                        modifier = Modifier.align(Alignment.End),
                    ) {
                        Text(text = "Forgot Password")
                    }
                }
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Thin,
                    fontSize = 20.sp,
                )
                TextButton(
                    onClick = { pageState.value = 2 },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue),
                ) {
                    Text(text = "Sign Up")
                }
            }

            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Spacer(modifier = Modifier.height(500.dp))
                Spacer(modifier = Modifier.height(50.dp))
                FilledTonalButton(
                    modifier = Modifier.width(200.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(buttonColor),
                    shape = RectangleShape,
                    onClick = {
                        scope.launch {
                            try {
                                auth.signInWithEmailAndPassword(email, password).await()
                                Log.d(TAG, "signInWithEmail:success")
                                user = auth.currentUser?.email.toString()
                                navController.navigate(route = Screen.GetItDone.route){
                                    popUpTo("Login"){
                                        inclusive = true
                                    }
                                }
                            } catch (e: FirebaseAuthException) {
                                // 显示错误消息
                                scaffoldState.snackbarHostState.showSnackbar(
                                    e.message ?: "Unknown error"
                                )
                            }
                        }
                    }
                ) {
                    Text(text = "Log in")
                }
            }
        }
    )
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
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            val userId = auth.currentUser?.email.toString()
                            user = auth.currentUser?.email.toString()
                            val db = Firebase.firestore
                            val userProfile = UserProfile(id = userId)
                            db.collection("User").document(userId).set(userProfile)
                            //转到用户界面
                            pageState.value = 1
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        }
                    }
            },
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign up + $email + $password")
        }
    }
}

@Composable
fun ForgotPassword(pageState: MutableState<Int>) {

    var email by remember { mutableStateOf("") }

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
            onClick = { pageState.value = 1 },
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Reset My Password")
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


