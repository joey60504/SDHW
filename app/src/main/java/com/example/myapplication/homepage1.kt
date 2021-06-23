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
        val datatest1 = listOf("Yes", "No")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, datatest1)
        findViewById<Spinner>(R.id.pet).adapter = adapter1

        val datatest2= listOf("Yes","No")
        val adapter2= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest2)
        findViewById<Spinner>(R.id.child).adapter=adapter2

        val datatest3= listOf("Man","Woman")
        val adapter3= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest3)
        findViewById<Spinner>(R.id.gender).adapter=adapter3

        val datatest4= listOf("Yes","No")
        val adapter4= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest4)
        findViewById<Spinner>(R.id.smoke).adapter=adapter4
    }
}