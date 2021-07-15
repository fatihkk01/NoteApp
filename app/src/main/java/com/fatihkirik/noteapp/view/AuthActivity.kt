package com.fatihkirik.noteapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fatihkirik.noteapp.databinding.ActivityAuthBinding
import com.fatihkirik.noteapp.model.User
import com.fatihkirik.noteapp.service.AccountManager
import com.fatihkirik.noteapp.service.AuthListener

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var accountManager: AccountManager
    private var userStatus:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        accountManager = AccountManager()

        userStatus = accountManager.checkCurrentUser()

        if(userStatus==true){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"Welcome !",Toast.LENGTH_LONG).show()
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val user = User(null,email,password)

            if(!email.equals("") && !password.equals("")){

                accountManager.signUp(user,object : AuthListener{
                    override fun onAuthSuccess() {
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(applicationContext,"Welcome !",Toast.LENGTH_LONG).show()
                    }
                    override fun onAuthFail(errorMessage: String) {
                        Toast.makeText(applicationContext,errorMessage,Toast.LENGTH_LONG).show()
                    }
                })

            }
            else{
                Toast.makeText(this,"Please fill in all fields !",Toast.LENGTH_LONG).show()
            }

        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val user = User(null,email,password)

            if(!email.equals("") && !password.equals("")){

                accountManager.signIn(user,object : AuthListener{
                    override fun onAuthSuccess() {
                        Toast.makeText(applicationContext,"Welcome !",Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    override fun onAuthFail(errorMessage: String) {
                        Toast.makeText(applicationContext,errorMessage,Toast.LENGTH_LONG).show()
                    }

                })

            }
            else{
                Toast.makeText(this,"Please fill in all fields !",Toast.LENGTH_LONG).show()
            }

        }


    }





}