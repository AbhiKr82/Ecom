package com.example.ecom.pages

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecom.AppUtil
import com.example.ecom.data
import com.example.ecom.model.ProductModel
import com.example.ecom.products
import com.example.ecom.stock
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType


@Composable
fun ProductDetailsPage(ProductId: String) {



    var product by remember {mutableStateOf(ProductModel()) }

    var context= LocalContext.current

    LaunchedEffect(Unit) {
        Firebase.firestore.collection(data).document(stock)
            .collection(products).document(ProductId).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var result = it.result.toObject(ProductModel::class.java)

                    if(result!=null) product=result
                }
            }
    }

    //Text(product.toString())

    Column(
        modifier = Modifier.fillMaxSize().padding(start = 30.dp, top = 50.dp, end = 30.dp, bottom = 35.dp)
            .verticalScroll(rememberScrollState())
        ,
    ) {
        Spacer(Modifier.height(40.dp))
        Text(product.title,
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(35.dp))
        Column(

        ) {
            val pagerState= rememberPagerState(0) {
                product.images.size
            }
            HorizontalPager(state = pagerState, pageSpacing = 20.dp) {
                AsyncImage(
                    model=product.images.get(it),
                    contentDescription = "Product Image",
                    modifier= Modifier
                        .fillMaxWidth().height(220.dp)
                        .clip(RoundedCornerShape(15.dp))
                )
            }
            Spacer(Modifier.height(5.dp))
            DotsIndicator(
                dotCount = product.images.size,
                pagerState = pagerState,
                type = ShiftIndicatorType(
                    dotsGraphic = DotGraphic(color = Color.Gray, size = 5.dp)
                )
            )
        }

        Spacer(Modifier.height(15.dp))
        Row(
            modifier= Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    product.Price,
                    fontSize = 16.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(Modifier.width(24.dp))
                Text(product.actualPrice,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold)
            }

            IconButton(
                onClick = {}
            ) {
                Icon(imageVector = Icons.Default.FavoriteBorder,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Add to Favorite")
            }
        }

        Spacer(Modifier.height(35.dp))
        Button(
            modifier= Modifier.fillMaxWidth().height(50.dp),
            onClick = {
                AppUtil.addItemToCart(ProductId,context = context)
            })
        {
            Text(
                text = "Add to Cart",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold

            )
        }
        Spacer(Modifier.height(35.dp))
        Text(text = "Product Description : \n", fontSize = 20.sp)
        Text(text = product.description, fontSize = 16.sp)

        Spacer(Modifier.height(8.dp))
        if(product.OtherDetails.isNotEmpty()) Text(text = "Other Details : \n", fontSize = 20.sp)

        product.OtherDetails.forEach {
            Text(text = "${it.key} : ${it.value}", fontSize = 16.sp)

        }

    }





}