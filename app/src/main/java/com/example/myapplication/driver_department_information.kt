package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_driver_department_information.*
import kotlinx.android.synthetic.main.activity_driver_department_information2.*
import kotlinx.android.synthetic.main.fragment_home.*

class driver_department_information : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_department_information)

        auth = FirebaseAuth.getInstance()
        var phone=auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        button4.setOnClickListener {
            val Date = findViewById<EditText>(R.id.editTextDate).text.toString()
            val startpoint = findViewById<EditText>(R.id.editTextTextPostalAddress).text.toString()
            val endpoint1 = findViewById<EditText>(R.id.editTextNumber2).text.toString()
            val carcard = findViewById<EditText>(R.id.editTextNumber).text.toString()
            val price=findViewById<EditText>(R.id.editTextNumber2).text.toString()
            imageButton56.setOnClickListener{
                val gender="限男"
                database.child("room").child(phone).setValue(gender)
            }
            imageButton53.setOnClickListener {
                val gender="限女"
                database.child("room").child(phone).setValue(gender)
            }
            imageButton39.setOnClickListener {
                val gender="皆可"
                database.child("room").child(phone).setValue(gender)
            }
            imageButton3.setOnClickListener {
                val smoke="可"
                database.child("room").child(phone).setValue(smoke)
            }
            imageButton32.setOnClickListener {
                val smoke="不可"
                database.child("room").child(phone).setValue(smoke)
            }
            imageButton4.setOnClickListener {
                val child="可"
                database.child("room").child(phone).setValue(child)
            }
            imageButton34.setOnClickListener {
                val child="不可"
                database.child("room").child(phone).setValue(child)
            }
            imageButton6.setOnClickListener {
                val pet="可"
                database.child("room").child(phone).setValue(pet)
            }
            imageButton38.setOnClickListener {
                val pet="不可"
                database.child("room").child(phone).setValue(pet)
            }
            if(Date.isNotEmpty() && startpoint.isNotEmpty() && endpoint1.isNotEmpty() && carcard.isNotEmpty() && price.isNotEmpty()){
                val roomInfo=roominfo(Date, startpoint, endpoint1, carcard,price)
                database.child("room").child(phone).setValue(roomInfo)
            }else{
                Toast.makeText(this, "規則不可為空", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun back2(p0: View){
        startActivity(Intent(this,HomeFragment::class.java))
    }
}