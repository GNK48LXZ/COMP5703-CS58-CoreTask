package com.example.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Purple200

@Composable
fun LoginScreen(){
    val isLoginByPassword = remember{
        mutableStateOf(false)
    }
    val isLogin = remember {
        mutableStateOf(false)
    }
    if(isLogin.value){
        MainPage()
    }
    else{
        Column(modifier = Modifier.fillMaxSize()) {
            NavigationBarSection(isLoginByPassword)
            Spacer(modifier = Modifier.height(50.dp))
            LoginSolo(isLoginByPassword)
            Spacer(modifier = Modifier.height(50.dp))
            InputSection(isLoginByPassword,isLogin)
        }
    }
}

@Composable
fun InputSection(loginByPassword: MutableState<Boolean>,isLogin:MutableState<Boolean>) {

    var phone:String by remember {
        mutableStateOf("")
    }
    var password:String by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    ) {
        SimpleOutlinedTextFieldSample("Phone Number")

        AnimatedVisibility(visible = loginByPassword.value) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                PasswordTextField()
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Forgot password?",
                    color = Color.Blue,
                    modifier = Modifier.clickable {  }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        MaterialTheme{
            Button(
                onClick={
                        isLogin.value = !isLogin.value;
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors()
            ){
                Text(
                    text = if(loginByPassword.value) "Login" else "Get verification code",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                text = "Don't have an account?",
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Sign up",
                color = Color.Blue,
                modifier = Modifier.clickable {  }
            )
        }
        if(loginByPassword.value){
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                onClick = { /* Do something! */ }
            ) {
                Icon(
                    painter =  painterResource(id = R.drawable.google),
                    contentDescription = "google",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text("Login with Google")
            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                onClick = { /* Do something! */ }
            ) {
                Icon(
                    painter =  painterResource(id = R.drawable.facebook),
                    contentDescription = "facebook",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text("Login with Facebook")
            }
        }
    }
}


@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SimpleOutlinedTextFieldSample(label:String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) }
    )
}

@Composable
fun PlayTextField(
    value:String,
    onValueChange:(String)->Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null
) {
    var focused by remember {
        mutableStateOf(false)
    }

    BasicTextField(value = value, onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                focused = it.isFocused
            }
    ){
        innerTextField ->
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ){
                if(value.isEmpty()){
                    Text(text = placeholder?:"", color = Purple200, fontSize = 16.sp)
                }
                innerTextField()
            }

            Divider(modifier = Modifier.fillMaxWidth(),
                color = if(focused) Color.Blue else Color.Gray)
        }
    }
}

@Composable
fun LoginSolo(loginByPassword: MutableState<Boolean>) {

    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    ) {
        if(loginByPassword.value){
            Text(text =  "Login By Password",
                maxLines = 1,
                fontSize = 40.sp,
                color = Color(0xFF333333))
        }
        else{
            Text(text =  "Login By Verification Code",
                maxLines = 1,
                fontSize = 30.sp,
                color = Color(0xFF333333))
        }
    }
}

@Composable
fun NavigationBarSection(loginByPassword: MutableState<Boolean>) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Icon(
            imageVector = Icons.Filled.Close,
            "Icon",
            modifier = Modifier
                .clickable {
                    loginByPassword.value = !loginByPassword.value
                }
                .padding(horizontal = 16.dp)
                .fillMaxHeight(),
            tint = Color(0xff333333)
        )

        Box(
            modifier = Modifier
                .clickable {
                    loginByPassword.value = !loginByPassword.value
                }
                .padding(horizontal = 16.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ){
            Text(text = if(loginByPassword.value) "Login with verification code" else "Login with password")
        }
    }
}
