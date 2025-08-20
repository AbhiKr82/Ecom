package com.example.ecom.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ecom.model.navItems
import com.example.ecom.pages.CartPage
import com.example.ecom.pages.Favorite
import com.example.ecom.pages.HomePage
import com.example.ecom.pages.ProfilePage
import com.example.ecom.viewModel.AuthViewModel

@Composable
fun HomeScreen(navController: NavController,authViewModel: AuthViewModel) {


    var selectedindex= remember { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar {

                    navItems.forEachIndexed { index,navItem->
                        NavigationBarItem(
                            selected = index==selectedindex.value,
                            onClick = {
                                selectedindex.value=index

                            },
                            icon = {
                                Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                            },
                            label = { Text(text = navItem.label) }
                        )
                    }


                }
            }
        ) {
            ContentScreen(modifier = Modifier.padding(it),selectedindex.value)

        }


    }


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedindex: Int,) {

    when(selectedindex){
        0-> HomePage(modifier)
        1-> ProfilePage(modifier)
        2-> Favorite(modifier)
        3-> CartPage(modifier)
    }


}