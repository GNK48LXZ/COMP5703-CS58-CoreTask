package com.example.myapplication


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

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
    else if (pageState.value == 4) {
        EditUserProfile_sign(pageState)
    }
}

var user = ""
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
        //modifier = Modifier
            //.padding(horizontal = 5.dp),
        //verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            Image(
                painter = painterResource(R.drawable.login_background),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 15.dp)
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
                                pageState.value = 4
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserProfile_sign(pageState: MutableState<Int>){
    var certificate by remember { mutableStateOf("") }
    var introduction by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var skill by remember { mutableStateOf("") }

    val openDialog = remember { mutableStateOf(false) }
    SignUpDialog(pageState,openDialog)

    val storage = Firebase.storage
    var storageRef = storage.reference
    val avatarImagesRef = storageRef.child("avatar/"+user+".jpg")

    LaunchedEffect(Unit) {
        // 监听指定Document ID的数据
        FireStore.collection("User")
            .document(user)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // 处理错误
                } else {
                    if (snapshot != null && snapshot.exists()) {
                        certificate = snapshot.getString("certificate")?:""
                        introduction = snapshot.getString("introduction")?:""
                        location = snapshot.getString("location")?:""
                        name = snapshot.getString("name")?:""
                        skill = snapshot.getString("skill")?:""
                    }
                }
            }
    }
    androidx.compose.material3.Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(background)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            val bitmap = RequestContentPermission()
            val baos = ByteArrayOutputStream()
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            }
            val data = baos.toByteArray()
            androidx.compose.material3.Text(
                "Name",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            androidx.compose.material3.TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            androidx.compose.material3.Text(
                "Introduction",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            androidx.compose.material3.TextField(
                value = introduction,
                onValueChange = { introduction = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            androidx.compose.material3.Text(
                "Address",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            androidx.compose.material3.TextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            androidx.compose.material3.Text(
                "Certificate",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            ElevatedButton(
                onClick = { /* Do something! */ },
                modifier = Modifier
                    .padding(16.dp)
                    .height(110.dp)
                    .width(160.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(textFieldColor)
            ) {
                androidx.compose.material3.Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(50.dp)
                )
            }
            androidx.compose.material3.Text(
                "Skill",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            androidx.compose.material3.TextField(
                value = skill,
                onValueChange = { skill = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = textFieldColor)
            )
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    val db = Firebase.firestore
                    val updateUserProfile = db.collection("User").document(user)
                    updateUserProfile.update("name", name)
                    updateUserProfile.update("introduction", introduction)
                    updateUserProfile.update("location", location)
                    updateUserProfile.update("skill", skill)
                    openDialog.value = true
                    if (bitmap != null)
                        avatarImagesRef.putBytes(data)
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(buttonColor)
            ) {
                androidx.compose.material3.Text("Save", fontSize = 20.sp)
            }
        }
    }
}


