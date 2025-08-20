package com.example.ecom.viewModel

import androidx.lifecycle.ViewModel
import com.example.ecom.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.example.ecom.user


class AuthViewModel: ViewModel() {

    private val _auth= Firebase.auth
    private val firestore= Firebase.firestore

    fun signup( email:String , password:String, name:String,onResult:(Boolean,String)->Unit){

        _auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userId=it.result?.user?.uid?:""

                    val userModel= User(userId,name,email)

                    firestore.collection(user).document(userId).set(userModel)
                        .addOnCompleteListener { task->
                            if(task.isSuccessful) onResult(true,"Account Created Successfully")
                            else onResult(false,task.exception?.message?:"Account creation Failed")

                        }
                }
                else{
                    onResult(false,it.exception?.message?:"Account creation Failed")

                }
            }

    }


    fun login(email:String,password:String,onResult:(Boolean,String)->Unit){
        _auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful) onResult(true,"Login Successfully")
                else onResult(false,it.exception?.message?:"Login Failed")

            }
    }
}