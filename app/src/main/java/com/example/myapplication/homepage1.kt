package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_test1.*

class homepage1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage1)
        val datatest= listOf("data1","data2","data3")
        val adapter= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest)
        findViewById<Spinner>(R.id.spinner3).adapter=adapter
    }

}