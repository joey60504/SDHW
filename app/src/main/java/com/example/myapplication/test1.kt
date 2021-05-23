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
        val datatest= listOf("data1","data2","data3")
        val adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,datatest)
        findViewById<Spinner>(R.id.spinner).adapter=adapter
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
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

}