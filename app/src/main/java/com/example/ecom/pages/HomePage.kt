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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecom.GlobalNavigation
import com.example.ecom._categoryProductPage
import com.example.ecom.banners
import com.example.ecom.categories
import com.example.ecom.data
import com.example.ecom.model.Category
import com.example.ecom.stock
import com.example.ecom.url
import com.example.ecom.user
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp),

    ) {
        //Text("HomePage ")
        HeaderView()
        BannerView()

        Text(text = "Categories ", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold))
        Spacer(Modifier.height(10.dp))
        CategoryView()
    }
}

@Composable
fun HeaderView(){

    var name = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {

        val userid= Firebase.auth.currentUser?.uid
        Firebase.firestore.collection(user).document(userid.toString()).get()
            .addOnSuccessListener {
            name.value = it.get("name").toString().split(" ").get(0)
        }

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Welcome !!",fontSize = 20.sp)
            Text(name.value,fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        }

        IconButton(onClick = {}) {
            Icon(imageVector = (Icons.Default.Search), contentDescription = "Search ", modifier = Modifier.size(35.dp))
        }
    }


}


@Composable
fun BannerView(modifier: Modifier = Modifier) {

    var bannerlist by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {

        Firebase.firestore.collection(data).document(banners).get()
            .addOnCompleteListener{
            bannerlist= it.result.get(url) as List<String>
        }
    }


    Column(

    ) {
        val pagerState= rememberPagerState(0) {
            bannerlist.size
        }
        HorizontalPager(state = pagerState, pageSpacing = 20.dp) {
            AsyncImage(
                model=bannerlist.get(it),
                contentDescription = "Banner Image",
                modifier= Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
            )
        }
        Spacer(Modifier.height(5.dp))
        DotsIndicator(
            dotCount = bannerlist.size,
            pagerState = pagerState,
            type = ShiftIndicatorType(
                dotsGraphic = DotGraphic(color = Color.Gray, size = 5.dp)
            )
        )
    }
}

@Composable
fun CategoryView(modifier: Modifier = Modifier) {

    val categoryList = remember {
        mutableStateOf<List<Category>>(emptyList())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection(data).document(stock).collection(categories)
            .get().addOnCompleteListener {
                if(it.isSuccessful){
                    val list= it.result.documents.mapNotNull { doc ->
                        doc.toObject(Category::class.java)
                    }
                    categoryList.value=list
                }
            }
    }

    LazyRow {
        items(categoryList.value){ item ->
            CategoryItem(item)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {

    Card(
        modifier = Modifier.padding(10.dp).size(120.dp)
            .clickable{
                GlobalNavigation.navController.navigate(_categoryProductPage+"/${category.id}")
            }
        ,
        shape = RoundedCornerShape(15.dp),

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = "Category Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
            Text(text = category.name)
        }
    }

}