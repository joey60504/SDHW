package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import kotlinx.android.synthetic.main.activity_registration1.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class driver_department_information : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_department_information)

        auth = FirebaseAuth.getInstance()
        var phone=auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
//        go.setOnClickListener {
//            val Date = findViewById<EditText>(R.id.editTextDate).text.toString()
//            val startpoint = findViewById<EditText>(R.id.editTextTextPostalAddress).text.toString()
//            val endpoint1 = findViewById<EditText>(R.id.editTextNumber2).text.toString()
//            val carcard = findViewById<EditText>(R.id.editTextNumber).text.toString()
//
//            if(Date.isEmpty() && startpoint.isEmpty() && endpoint1.isEmpty() && carcard.isEmpty()){
//                Toast.makeText(this, "規則不可為空", Toast.LENGTH_SHORT).show()
//            }
//
//            button4.setOnClickListener {
//                val price=findViewById<EditText>(R.id.editTextNumber2).text.toString()
//                imageButton56.setOnClickListener{
//                    val gender="限男"
//                    database.child("room").child(phone).setValue(gender)
//                }
//                imageButton53.setOnClickListener {
//                    val gender="限女"
//                    database.child("room").child(phone).setValue(gender)
//                }
//                imageButton39.setOnClickListener {
//                    val gender="皆可"
//                    database.child("room").child(phone).setValue(gender)
//                }
//                imageButton3.setOnClickListener {
//                    val smoke="可"
//                    database.child("room").child(phone).setValue(smoke)
//                }
//                imageButton32.setOnClickListener {
//                    val smoke="不可"
//                    database.child("room").child(phone).setValue(smoke)
//                }
//                imageButton4.setOnClickListener {
//                    val child="可"
//                    database.child("room").child(phone).setValue(child)
//                }
//                imageButton34.setOnClickListener {
//                    val child="不可"
//                    database.child("room").child(phone).setValue(child)
//                }
//                imageButton6.setOnClickListener {
//                    val pet="可"
//                    database.child("room").child(phone).setValue(pet)
//                }
//                imageButton38.setOnClickListener {
//                    val pet="不可"
//                    database.child("room").child(phone).setValue(pet)
//                }
//                val roomInfo=roominfo(Date, startpoint, endpoint1, carcard, price)
//                database.child("room").child(phone).setValue(roomInfo)
//                    .addOnCompleteListener {
//                        Toast.makeText(this, "房間建立成功", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this, choice::class.java))
//                    }
//            }
//
//
//        }

    }

    fun back2(p0: View){
        startActivity(Intent(this,HomeFragment::class.java))
    }
    fun datePicker(v: View) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        DatePickerDialog(v.context, { view, year, month, day ->
            val monthfix= month+1
            val dateTime = "$year/$monthfix/$day"
            editTextDate.setText(dateTime)
        }, year, month, day
        ).show()
    }
    fun timePicker(v:View){
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR]
        val minute = calendar[Calendar.MINUTE]
        TimePickerDialog(v.context, {_, hour, minute ->
            val dateTime = "$hour:$minute"
            editTextDate2.setText(dateTime)
        },hour,minute,true
        ).show()
    }
}





