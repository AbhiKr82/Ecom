package com.example.ecom.model

data class User(
    var userid:String="",
    var name:String="",
    var email:String="",
    var CartItems:Map<String,Long> = emptyMap()

)
