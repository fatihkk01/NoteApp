package com.fatihkirik.noteapp.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fatihkirik.noteapp.R
import com.fatihkirik.noteapp.databinding.ActivityMainBinding
import com.fatihkirik.noteapp.service.AccountManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var accountManager: AccountManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        accountManager = AccountManager()

        val bottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController  = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.signOut){
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Signout")
            dialog.setMessage("Are you sure ?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                accountManager.signOut()
                val intent = Intent(this,AuthActivity::class.java)
                startActivity(intent)
                finish()
            } )

            dialog.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this,"Signout canceled",Toast.LENGTH_LONG).show()
            })

            dialog.show()
        }

        return super.onOptionsItemSelected(item)
    }

}