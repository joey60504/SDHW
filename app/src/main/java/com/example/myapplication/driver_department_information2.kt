package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_driver_department_information2.*
import kotlin.random.Random

class driver_department_information2 : AppCompatActivity() {
    var gender=""
    var smoke=""
    var child=""
    var pet=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_department_information2)
        lateinit var auth: FirebaseAuth
        b.setOnClickListener {
            gender="限男"
            iconselect()
        }
        g.setOnClickListener {
            gender="限女"
            iconselect()
        }
        bg.setOnClickListener {
            gender="皆可"
            iconselect()
        }
        smoke_yes.setOnClickListener {
            smoke="可"
            iconselect()
        }
        smoke_no.setOnClickListener {
            smoke="不可"
            iconselect()
        }
        child_yes.setOnClickListener {
            child="可"
            iconselect()
        }
        child_no.setOnClickListener {
            child="不可"
            iconselect()
        }

        pet_yes.setOnClickListener {
            pet="可"
            iconselect()
        }
        pet_no.setOnClickListener {
            pet="不可"
            iconselect()
        }


        button4.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val Date = intent.getBundleExtra("Extra")?.getString("Date_EXTRA")
            val time = intent.getBundleExtra("Extra")?.getString("time_EXTRA")
            val startpoint = intent.getBundleExtra("Extra")?.getString("startpoint_EXTRA")
            val endpoint1 = intent.getBundleExtra("Extra")?.getString("endpoint_EXTRA")
            val carcard = intent.getBundleExtra("Extra")?.getString("carcard_EXTRA")
            val peoplelimit=intent.getBundleExtra("Extra")?.getString("peoplelimit_EXTRA").toString()
            val other =findViewById<EditText>(R.id.editTextTextPersonName6).text.toString()
            val price = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
            val number= Random.nextInt(10000001,99999999).toString()
            val driversphone=phone

            val roomInfo = roominfo(Date, time, startpoint, endpoint1, carcard, price,number,peoplelimit,other,driversphone)
            val roomRule=roomrule(gender, smoke, child, pet)
            database.child("room").child(phone).child("roomRULE").setValue(roomRule)
            database.child("room").child(phone).child("roomINFO").setValue(roomInfo)
                .addOnCompleteListener {
                        Toast.makeText(this, "房間建立成功", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, choice::class.java))
                }


        }
    }

    fun iconselect(){
        when(gender){
            "限男"->{
                b.setImageResource(R.drawable.boy)
                bg.setImageResource(R.drawable.man_woman_n)
                g.setImageResource(R.drawable.girl_n)
            }
            "限女"->{
                b.setImageResource(R.drawable.boy_n)
                bg.setImageResource(R.drawable.man_woman_n)
                g.setImageResource(R.drawable.girl)
            }
            "皆可"->{
                b.setImageResource(R.drawable.boy_n)
                bg.setImageResource(R.drawable.boy_girl1)
                g.setImageResource(R.drawable.girl_n)
            }
        }
        when(smoke){
            "可"->{
                smoke_yes.setImageResource(R.drawable.check_y)
                smoke_no.setImageResource(R.drawable.cross)
            }
            "不可"->{
                smoke_yes.setImageResource(R.drawable.check_n)
                smoke_no.setImageResource(R.drawable.cross_y)
            }
        }
        when(child){
            "可"->{
                child_yes.setImageResource(R.drawable.check_y)
                child_no.setImageResource(R.drawable.cross)
            }
            "不可"->{
                child_yes.setImageResource(R.drawable.check_n)
                child_no.setImageResource(R.drawable.cross_y)
            }
        }
        when(pet){
            "可"->{
                pet_yes.setImageResource(R.drawable.check_y)
                pet_no.setImageResource(R.drawable.cross)
            }
            "不可"->{
                pet_yes.setImageResource(R.drawable.check_n)
                pet_no.setImageResource(R.drawable.cross_y)
            }
        }
    }
}