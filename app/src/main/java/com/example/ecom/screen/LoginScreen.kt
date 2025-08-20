package com.example.ecom.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecom.AppUtil
import com.example.ecom.R
import com.example.ecom._authScreen
import com.example.ecom._homeScreen
import com.example.ecom._singUpScreen
import com.example.ecom.viewModel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController,authViewModel: AuthViewModel) {

    var email =  remember { mutableStateOf("") }
    var password =  remember { mutableStateOf("") }
    var isloading= remember { mutableStateOf(false) }

    val context = LocalContext.current



    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Hello there !! \n",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Text(text = "Welcome back \n",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        Image(
            painter = painterResource(R.drawable.login),
            contentDescription = "Banner Image"
            , modifier = Modifier.fillMaxWidth().height(170.dp)

        )

        Spacer(Modifier.height(60.dp))

        OutlinedTextField(
            value = email.value
            , onValueChange = {email.value = it}
            , label = { Text("Enter Email") },
            maxLines = 1
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = password.value
            , onValueChange = {password.value = it}
            , label = { Text("Enter Password") },
            maxLines = 1
        )

        Spacer(Modifier.height(25.dp))

        Button(
            onClick = {
                isloading.value=true
                authViewModel.login(email.value,password.value){
                    success, message->
                    if(success){
                        isloading.value=false
                        navController.navigate(_homeScreen){
                            popUpTo(_authScreen){
                                inclusive=true
                            }

                        }
                        AppUtil.showToast(context,message)
                    }else{
                        isloading.value=false
                        AppUtil.showToast(context,message)
                    }

                }


            },
            modifier = Modifier.height(50.dp).width(250.dp),
            shape = RoundedCornerShape(15.dp),
            enabled = !isloading.value
        ) {
            Text(text = if(isloading.value)"Logging in..." else "Login",fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(10.dp))

        TextButton(
            onClick = {navController.navigate(_singUpScreen)},

        ) {
            Text("Don't have an Account , SignUp",fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }




    }

}