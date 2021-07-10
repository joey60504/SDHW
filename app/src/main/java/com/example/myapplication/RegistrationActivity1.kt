package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration1.*
import java.util.*

class RegistrationActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration1)
        var database = FirebaseDatabase.getInstance().reference


        registerButton.setOnClickListener() {
            val name = findViewById<EditText>(R.id.firstnameInput).text.toString()
            val age = findViewById<EditText>(R.id.lastnameInput).text.toString()
            val gender = findViewById<EditText>(R.id.usernameInput).text.toString()
            val photo = findViewById<EditText>(R.id.passwordInput).text.toString()

            if (name.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() && photo.isNotEmpty()) {
                val Users=User(name, age, gender, photo)
                database.child(name).setValue(Users)
                    .addOnCompleteListener {
                        Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, choice::class.java))
                    }


            } else {
                Toast.makeText(this, "欄位不可為空", Toast.LENGTH_SHORT).show()
            }


        }
    }
}


