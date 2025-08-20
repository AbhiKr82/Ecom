package com.example.ecom

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

object AppUtil {
    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun addItemToCart( productId:String,context: Context){

        val UserDoc= Firebase.firestore.collection(user)
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        UserDoc.get().addOnCompleteListener {
            if(it.isSuccessful){
                val cartItem= it.result.get("CartItems") as? Map<String,Long>?:emptyMap()

                val currentQuantity= cartItem[productId]?:0
                val updatedQuantity= currentQuantity+1

                val updatedCart= mapOf("CartItems.$productId" to updatedQuantity)

                UserDoc.update(updatedCart).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(context,"Item added to cart")
                    }else{
                        showToast(context,"Failed to add item to cart")
                    }
                }


            }
        }
    }
    fun RemoveItemFromCart( productId:String,context: Context,boolean: Boolean=false){

        val UserDoc= Firebase.firestore.collection(user)
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        UserDoc.get().addOnCompleteListener {
            if(it.isSuccessful){
                val cartItem= it.result.get("CartItems") as? Map<String,Long>?:emptyMap()

                val currentQuantity= cartItem[productId]?:0
                val updatedQuantity= currentQuantity-1



                val updatedCart= if(updatedQuantity.toInt() <=0 || boolean)  mapOf("CartItems.$productId" to FieldValue.delete())
                    else mapOf("CartItems.$productId" to updatedQuantity)

                UserDoc.update(updatedCart).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(context,"Item removed from added to cart")
                    }else{
                        showToast(context,"Failed to removed from item to cart")
                    }
                }


            }
        }
    }


}