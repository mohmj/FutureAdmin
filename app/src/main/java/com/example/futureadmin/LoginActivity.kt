package com.example.futureadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futureadmin.Models.UserInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var auth= Firebase.auth
        var uid=auth.uid

        login_activity_button.setOnClickListener(){
            var email=login_activity_email_edit_text.text.toString()
            var password=login_activity_password_edit_text.text.toString()
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                var intetn=Intent(this,MainActivity::class.java)
                intetn.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intetn)
            }
        }
    }
}