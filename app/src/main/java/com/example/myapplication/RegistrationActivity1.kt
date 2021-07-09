package com.example.myapplication

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

class RegistrationActivity1 : AppCompatActivity() {
    private lateinit var database:FirebaseDatabase
    private lateinit var referance:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration1)

        database = FirebaseDatabase.getInstance();
        referance = database.getReference("proflie")
        registerButton.setOnClickListener(){
            send()
        }
    }
    private fun send(){
        val name=findViewById<EditText>(R.id.firstnameInput).text.toString()
        val age=findViewById<EditText>(R.id.lastnameInput).text.toString()
        val gender=findViewById<EditText>(R.id.usernameInput).text.toString()
        val photo=findViewById<EditText>(R.id.passwordInput).text.toString()
        if(name.isNotEmpty()&&age.isNotEmpty()&&gender.isNotEmpty()&&photo.isNotEmpty())
        {
            val Users=User(name,age,gender,photo)
            val id=referance.push().key
            referance.child(id!!).setValue(Users)
            firstnameInput.setText("")
            lastnameInput.setText("")
            usernameInput.setText("")
            passwordInput.setText("")

        }else{

            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


        }


    }

}