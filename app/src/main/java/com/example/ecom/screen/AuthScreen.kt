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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecom.R
import com.example.ecom._loginScreen
import com.example.ecom._singUpScreen


@Composable
fun AuthScreen(navController: NavController) {

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Image(
            painter = painterResource(R.drawable.bannar),
            contentDescription = "Banner Image"
            , modifier = Modifier.fillMaxWidth().height(200.dp)

        )


        Spacer(Modifier.height(20.dp))
        Text(text = "Welcome to E-commerce \n", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
        Text(text = " Your shopping destination",fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        Spacer(Modifier.height(50.dp))

        Button(
            onClick = {navController.navigate(_loginScreen)},
            modifier = Modifier.height(50.dp).width(250.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("Login",fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(15.dp))


        OutlinedButton(
            onClick = {navController.navigate(_singUpScreen)},
            modifier = Modifier.height(50.dp).width(250.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("SignUp",fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }

    }

}