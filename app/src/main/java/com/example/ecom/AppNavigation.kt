package com.example.ecom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecom.pages.CategoryProductPage
import com.example.ecom.pages.ProductDetailsPage
import com.example.ecom.screen.AuthScreen
import com.example.ecom.screen.HomeScreen
import com.example.ecom.screen.LoginScreen
import com.example.ecom.screen.SignUpScreen
import com.example.ecom.viewModel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(innerPadding: PaddingValues) {

    val navController = rememberNavController()
    GlobalNavigation.navController=navController
    val authViewModel: AuthViewModel = viewModel()

    val isLogggedin= Firebase.auth.currentUser!=null

    NavHost(
       navController= navController, startDestination = if(isLogggedin) _homeScreen else _authScreen
    ) {
        composable(_authScreen) {
            AuthScreen(navController)
        }
        composable(_loginScreen){
            LoginScreen(navController,authViewModel)
        }
        composable(_singUpScreen){
            SignUpScreen(navController,authViewModel)
        }
        composable(_homeScreen){
            HomeScreen(navController,authViewModel)
        }

        composable(_categoryProductPage+"/{categoryId}"){
            var categoryId= it.arguments?.getString("categoryId").toString()
            CategoryProductPage(categoryId)
        }
        composable(_productDetailsPage+"/{ProductId}"){
            var ProductId= it.arguments?.getString("ProductId").toString()
            ProductDetailsPage(ProductId)
        }
    }
}

object  GlobalNavigation{
    lateinit var navController: NavHostController
}