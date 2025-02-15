package com.mich.mvvmlogion.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mich.mvvmlogion.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel){
Box(modifier = Modifier
    .fillMaxSize()
    .padding(16.dp)){
    Login(Modifier.align(Alignment.Center), viewModel)
   }
 }

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable:Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isloading:Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isloading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }

    }else{
        Column( modifier = Modifier) {
            HederImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = modifier.padding(16.dp))

            EmailField(email) {viewModel.onLoginChanged(it, password)}
            Spacer(modifier = modifier.padding(4.dp))

            PasswoerdField(password) {viewModel.onLoginChanged(email, it)}
            Spacer(modifier = Modifier.padding(8.dp))

            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable){
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = {onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color(0xFFFF4303),
        disabledBackgroundColor = Color(0xFFF78058),
        contentColor = Color.White,
        disabledContentColor = Color.White
    ), enabled = loginEnable
    ) {
        Text(text = "Iniciar Sesion")
    }
}

@Composable
fun ForgotPassword( modifier: Modifier) {
    Text(text = "Olvidaste la Contraseña?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)

    )

}

@Composable
fun PasswoerdField(password: String, onTextFieldChanged: (String) -> Unit) {
   TextField(
       value = password, onValueChange = {onTextFieldChanged(it)},
       placeholder = { Text(text = "Contraseña")},
       modifier = Modifier.fillMaxWidth(),
       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
       singleLine = true,
       maxLines = 1,
       colors = TextFieldDefaults.textFieldColors(
           textColor = Color(0xFF636262),
           backgroundColor = Color(0xFFDEDDDD),
           focusedIndicatorColor = Color.Transparent,
           unfocusedIndicatorColor = Color.Transparent
       )
   )
}


@Composable
fun EmailField(email:String, onTextFieldChanged:(String) -> Unit) {

    TextField(
        value = email, onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            backgroundColor = Color(0xFFDEDDDD),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
      )
    )
}

@Composable
fun HederImage(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.img_fuego),
        contentDescription = "Header",
    modifier = Modifier)
}
