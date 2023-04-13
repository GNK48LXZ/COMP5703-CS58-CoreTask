package com.example.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Purple200

@Composable
fun login() {
    val pageState = remember {
        mutableStateOf(1)
    }
    if (pageState.value == 1) {
        LoginScreen(pageState)
    } else if (pageState.value == 2) {
        SignUpScreen(pageState)
    }else if (pageState.value == 3) {
        ForgotPassword(pageState)
    }


}

@Composable
fun LoginScreen(pageState: MutableState<Int>) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Login to Your Account!",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(60.dp))
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
        Spacer(modifier = Modifier.height(25.dp))
        TextButton(
            onClick = {pageState.value = 3},
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Forgot Password")
        }
        Button(
            onClick = { /* Handle login button click */ },
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log in")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Don't have an account?",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Thin,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextButton(
            onClick = {pageState.value = 2},
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
fun SignUpScreen(pageState: MutableState<Int>) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Row(){
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
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
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
            onClick = {pageState.value = 1},
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


