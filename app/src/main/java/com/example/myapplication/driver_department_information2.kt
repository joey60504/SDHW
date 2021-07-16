package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.ui.home.HomeFragment

class driver_department_information2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_department_information2)
    }
    fun back3(p0: View){
        startActivity(Intent(this, driver_department_information::class.java))
    }
}