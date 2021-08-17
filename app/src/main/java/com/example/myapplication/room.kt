package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

lateinit var auth: FirebaseAuth
lateinit var data:String
class room : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        dataselect()
    }

    fun dataselect(){
        data = intent.getStringExtra("Data").toString()
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference

    }
}