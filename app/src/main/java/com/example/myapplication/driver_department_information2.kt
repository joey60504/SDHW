package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_driver_department_information2.*

class driver_department_information2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_department_information2)
        lateinit var auth: FirebaseAuth
        var gender=""
        var smoke=""
        var child=""
        var pet=""
        b.setOnClickListener {
            gender="限男"
            bg.setImageResource(R.drawable.man_woman_n)
            g.setImageResource(R.drawable.girl_n)
        }
        g.setOnClickListener {
            gender="限女"
            b.setImageResource(R.drawable.boy_n)
            bg.setImageResource(R.drawable.man_woman_n)
        }
        bg.setOnClickListener {
            gender="皆可"
            b.setImageResource(R.drawable.boy_n)
            g.setImageResource(R.drawable.girl_n)
        }

        smoke_yes.setOnClickListener {
            smoke="可"
            smoke_no.setImageResource(R.drawable.cross)
        }
        smoke_no.setOnClickListener {
            smoke="不可"
            smoke_yes.setImageResource(R.drawable.check_n)
        }

        child_yes.setOnClickListener {
            child="可"
            child_no.setImageResource(R.drawable.cross)
        }
        child_no.setOnClickListener {
            child="不可"
            child_yes.setImageResource(R.drawable.check_n)
        }

        pet_yes.setOnClickListener {
            pet="可"
            pet_no.setImageResource(R.drawable.cross)
        }
        pet_no.setOnClickListener {
            pet="不可"
            pet_yes.setImageResource(R.drawable.check_n)
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
            val price = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()

            val roomInfo = roominfo(Date, time, startpoint, endpoint1, carcard, price)
            val roomRule=roomrule(gender, smoke, child, pet)
            database.child("room").child(phone).child("roomRULE").setValue(roomRule)
            database.child("room").child(phone).child("roomINFO").setValue(roomInfo)
                .addOnCompleteListener {
                    Toast.makeText(this, "房間建立成功", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, choice::class.java))
                }
        }
    }
    fun back3(p0: View){
        startActivity(Intent(this, driver_department_information::class.java))
    }
}