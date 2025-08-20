package com.example.ecom.model

data class ProductModel(
    val id:String="",
    val Categories:String ="",
    val title:String="",
    val description:String="",
    val Price:String="",
    val actualPrice :String="",
    val images:List<String> = emptyList(),
    val OtherDetails:Map<String,String> = emptyMap()
)
