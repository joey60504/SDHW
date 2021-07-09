package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_personal_details.*

class AddPersonalDetails : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseRef: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_personal_details)

        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance()
        val ref = databaseRef?.reference!!.child("profile")

        val Login = findViewById<Button>(R.id.loginBtn)
        val firstnameInput : EditText = findViewById(R.id.firstnameInput)
        val lastnameInput : EditText = findViewById(R.id.lastnameInput)
        val usernameInput : EditText = findViewById(R.id.usernameInput)
        val passwordInput : EditText = findViewById(R.id.passwordInput)

        Login.setOnClickListener{
           val firstName = firstnameInput.text.toString()
            val lastname = lastnameInput.text.toString()
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            val tableRef = ref.child(auth?.currentUser?.uid!!)
            tableRef?.child("first_name").setValue(firstName)
            tableRef?.child("last_name").setValue(lastname)
            tableRef?.child("user_name").setValue(username)
            tableRef?.child("password").setValue(password)

            finish()
    }



    }
}
