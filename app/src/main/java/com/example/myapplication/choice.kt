package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_personinformation.*

class choice : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)
//登入
        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
        val logout=findViewById<Button>(R.id.idLogout)
        if(currentUser==null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        logout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
//完
//換頁
    }
    fun commute(p0: View){
        startActivity(Intent(this,homepage::class.java))
    }
    fun test(p0: View){
        startActivity(Intent(this,ProfileActivity::class.java))
    }
//換頁完
}
