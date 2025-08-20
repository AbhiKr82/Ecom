package com.example.ecom.pages

import android.R.attr.onClick
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.ecom.categories
import com.example.ecom.data
import com.example.ecom.model.ProductModel
import com.example.ecom.products
import com.example.ecom.stock
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun CategoryProductPage(categoryId:String) {
    val productList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection(data).document(stock).collection(products)
            .whereEqualTo(categories,categoryId)
            .get().addOnCompleteListener {
                if(it.isSuccessful){
                    val list= it.result.documents.mapNotNull { doc ->
                        doc.toObject(ProductModel::class.java)
                    }
                    productList.value=list
                }
            }
    }

    LazyColumn(
        modifier= Modifier.fillMaxSize().padding(top = 50.dp, start = 10.dp, end = 10.dp)
    ) {

        items(productList.value.chunked(2)){ rowitem->
            Row {
                rowitem.forEach { item ->
                    ProductitemView(item, modifier = Modifier.weight(1f))

                }
                if(rowitem.size==1){
                    Spacer(Modifier.weight(1f))
                }
            }


        }
    }

}


@Composable
fun ProductitemView(item: ProductModel,modifier: Modifier = Modifier) {

    var context= LocalContext.current
    Card(
        modifier=modifier.padding(10.dp).
        clickable(
            onClick = {
                GlobalNavigation.navController.navigate(_productDetailsPage+"/${item.id}")
            }
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column (
            modifier= Modifier.padding(15.dp)
            ){
            AsyncImage(
                model = item.images.firstOrNull(),
                contentDescription = item.title,
                modifier= Modifier.height(120.dp)
            )
            Text(
                text = item.title,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 10.dp)
            )

            Row(
                modifier= Modifier.fillMaxWidth().padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(item.Price, fontSize = 16.sp, style = TextStyle(textDecoration = TextDecoration.LineThrough))
                    Spacer(Modifier.height(4.dp))
                    Text(item.actualPrice, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                IconButton(
                    onClick = {
                        AppUtil.addItemToCart(item.id,context )
                    }
                ) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Add to cart")
                }
            }

        }
    }


}