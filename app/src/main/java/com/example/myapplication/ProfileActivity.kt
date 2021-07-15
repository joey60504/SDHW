package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference

        val signOutButton: Button = findViewById(R.id.signOutButton)
        val phoneNumber: TextView = findViewById(R.id.phoneNumber)
        val addProfileButton: Button = findViewById(R.id.addProfileButton)
        //print phone number
        phoneNumber.text = "logged in user phone number:" + auth.currentUser?.phoneNumber.toString()

        signOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        addProfileButton.setOnClickListener {
            val intent = Intent(this@ProfileActivity, RegistrationActivity1::class.java)
            startActivity(intent)

        }
    }
}