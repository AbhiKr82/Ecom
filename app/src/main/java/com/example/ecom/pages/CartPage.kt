package com.example.ecom.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecom.AppUtil
import com.example.ecom.GlobalNavigation
import com.example.ecom._productDetailsPage
import com.example.ecom.data
import com.example.ecom.model.ProductModel
import com.example.ecom.model.User
import com.example.ecom.products
import com.example.ecom.stock
import com.example.ecom.user
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CartPage(modifier: Modifier = Modifier) {

    val userData= remember { mutableStateOf(User()) }

    DisposableEffect(key1 = Unit) {
        val Listener= Firebase.firestore.collection(user).document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener { it,_ ->
                if(it!=null){
                    val result= it.toObject(User::class.java)
                    if(result!=null) userData.value=result
                }
            }

        onDispose { Listener.remove() }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 6.dp),


    ) {
        Spacer(Modifier.height(20.dp))
        Text(text = "      Your Cart",
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp
        )

        Spacer(Modifier.height(20.dp))

        LazyColumn {
            items(userData.value.CartItems.toList(),key={it.first}){(productId,Qty) ->

                CartItemView(productId,Qty)

            }
        }

    }
}


@Composable
fun CartItemView(productId:String , Qty:Long) {

    var product by remember {
        mutableStateOf(ProductModel())
    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection(data).document(stock).collection(products)
            .document(productId).get().addOnCompleteListener {
                if(it.isSuccessful){
                    val result= it.result.toObject(ProductModel::class.java)
                    if(result!=null) product=result
                }
            }
    }


    var context= LocalContext.current
    Card(
        modifier=Modifier.padding(5.dp).fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row (
            modifier= Modifier.padding(15.dp)
        ){

            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier= Modifier.height(100.dp).width(100.dp)
            )
            Spacer(Modifier.width(10.dp))
            Column(
                modifier = Modifier.padding(5.dp).width(185.dp)
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Spacer(Modifier.height(10.dp))

                Text(text = product.actualPrice, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        AppUtil.RemoveItemFromCart(productId,context)
                    }) {
                        Text(text = "-", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Text(text = "$Qty",fontWeight = FontWeight.Bold)
                    IconButton(onClick = {
                        AppUtil.addItemToCart(productId,context)
                    }) {
                        Text(text = "+", fontSize = 20.sp,fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            IconButton(
                onClick = {
                    AppUtil.RemoveItemFromCart(productId,context,true)
                }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }

        }
    }


}