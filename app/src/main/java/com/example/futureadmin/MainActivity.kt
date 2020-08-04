package com.example.futureadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futureadmin.Services.Categories
import com.example.futureadmin.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var auth=Firebase.auth

            checkLogin()

        var category=Categories()

        main_activity_special_offers_container_layout.setOnClickListener(){
            addProductActivity(category.special_offers,"Special offers")
        }
        main_activity_computer_container_layout.setOnClickListener(){
            startActivity(Intent(this,ComputerPartsActivity::class.java))
        }
        main_activity_laptop_container_layout.setOnClickListener(){
            addProductActivity(category.laptop,"Laptop")
        }
        main_activity_console_container_layout.setOnClickListener(){
            addProductActivity(category.console,"Console")
        }
        main_activity_programs_and_games_container_layout.setOnClickListener(){
            addProductActivity(category.programs_and_games,"Programs and games")
        }
        main_activity_signout_container_layout.setOnClickListener(){
            auth.signOut()
            checkLogin()
        }



    }
    fun checkLogin(){
        var uid=Firebase.auth.uid
        if(uid==null){
        var intetn= Intent(this,LoginActivity::class.java)
        intetn.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intetn)
        }
    }

    fun addProductActivity(category:String,categoryTitle: String){
        var intent=Intent(this,AddProductActivity::class.java)
        intent.putExtra(Database().category,category)
        intent.putExtra(Database().categoryTitle,categoryTitle)
        startActivity(intent)
    }
}