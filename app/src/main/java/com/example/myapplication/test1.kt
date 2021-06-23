package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_test1.*

class test1 : AppCompatActivity(),AdapterView.OnItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
        val datatest1= listOf("Yes","No")
        val adapter1=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest1)
        findViewById<Spinner>(R.id.spinner).adapter=adapter1
        spinner.onItemSelectedListener=this


        setContentView(R.layout.activity_test1)
        val datatest2= listOf("Yes","No")
        val adapter2=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest2)
        findViewById<Spinner>(R.id.spinner).adapter=adapter2
        spinner.onItemSelectedListener=this

        setContentView(R.layout.activity_test1)
        val datatest3= listOf("Man","Woman")
        val adapter3=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest3)
        findViewById<Spinner>(R.id.spinner).adapter=adapter3
        spinner.onItemSelectedListener=this

        setContentView(R.layout.activity_test1)
        val datatest4= listOf("Yes","No")
        val adapter4=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest4)
        findViewById<Spinner>(R.id.spinner).adapter=adapter4
        spinner.onItemSelectedListener=this
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        val result = parent.getItemAtPosition(pos).toString()
        Log.d("DEBUG", result)

        when(result){
            "data1"->{
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragment,BlankFragment1()).commit()
            }
            "data2"->{
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragment,BlankFragment2()).commit()
            }
            "data3"->{
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragment,BlankFragment3()).commit()
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

}