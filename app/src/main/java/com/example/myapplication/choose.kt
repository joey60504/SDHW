package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.sign

class choose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //      findViewById<TextView>(R.id.textView2).setOnClickListener {
        //        startActivity(Intent(this,signin::class.java)) }
    }
    fun choose(p0: View){
        startActivity(Intent(this,homepage::class.java))
    }

}