package com.example.ecom.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label:String,
    val icon: ImageVector
)

val navItems = listOf(
    NavItem("Home", Icons.Default.Home),
    NavItem("Profile", Icons.Default.Person),
    NavItem("Favorite", Icons.Default.Favorite),
    NavItem("Cart", Icons.Default.ShoppingCart)
)