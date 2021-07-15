package com.fatihkirik.noteapp.service

import android.widget.Toast
import com.fatihkirik.noteapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AccountManager {

    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore
    init {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    fun signIn(user:User,authListener: AuthListener){

        auth.signInWithEmailAndPassword(user.email,user.password).addOnSuccessListener {

            authListener.onAuthSuccess()

        }.addOnFailureListener { e ->

            authListener.onAuthFail(e.localizedMessage.toString())

        }

    }


    fun signUp(user:User,authListener: AuthListener){

        auth.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener {

            if(it.exception!=null){
                authListener.onAuthFail(it.exception!!.localizedMessage.toString())
            }
            else{
                val newUser = hashMapOf<String,Any>()
                newUser.put("id",auth.currentUser!!.uid)
                newUser.put("email",user.email)

                db.collection("Users").document(auth.currentUser!!.uid).set(newUser).addOnSuccessListener {

                    authListener.onAuthSuccess()

                }.addOnFailureListener { e ->
                    if(e!=null){
                        authListener.onAuthFail(e.localizedMessage.toString())
                    }
                }

            }

        }

    }

    fun signOut(){
        if(checkCurrentUser()){
            auth.signOut()
        }
    }

    fun checkCurrentUser() : Boolean {

        var user = auth.currentUser

        if(user!=null){
            return true
        }
        else{
            return false
        }

    }

}