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
        button4.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val Date = intent.getBundleExtra("Extra")?.getString("Date_EXTRA")
            val time = intent.getBundleExtra("Extra")?.getString("time_EXTRA")
            val startpoint = intent.getBundleExtra("Extra")?.getString("startpoint_EXTRA")
            val endpoint1 = intent.getBundleExtra("Extra")?.getString("endpoint_EXTRA")
            val carcard = intent.getBundleExtra("Extra")?.getString("carcard_EXTRA")
            val price = findViewById<EditText>(R.id.editTextNumber2).text.toString()

            val roomInfo = roominfo(Date, time, startpoint, endpoint1, carcard, price)
            database.child("room").child(phone).setValue(roomInfo)
                .addOnCompleteListener {
                    Toast.makeText(this, "房間建立成功", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, choice::class.java))
                }
        }
        imageButton56.setOnClickListener{
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val gender:String?="限男"
            database.child("room").child(phone).child("roomrule").child("gender").setValue(gender)
        }
        imageButton53.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val gender="限女"
            database.child("room").child(phone).child("roomrule").child("gender").setValue(gender)
        }
        imageButton39.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val gender="皆可"
            database.child("room").child(phone).child("roomrule").child("gender").setValue(gender)
        }
        imageButton3.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val smoke="可"
            database.child("room").child(phone).child("roomrule").child("smoke").setValue(smoke)
        }
        imageButton32.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val smoke="不可"
            database.child("room").child(phone).child("roomrule").child("smoke").setValue(smoke)
        }
        imageButton4.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val child="可"
            database.child("room").child(phone).child("roomrule").child("child").setValue(child)
        }
        imageButton34.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val child="不可"
            database.child("room").child(phone).child("roomrule").child("child").setValue(child)
        }
        imageButton6.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val pet="可"
            database.child("room").child(phone).child("roomrule").child("pet").setValue(pet)
        }
        imageButton38.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            val pet="不可"
            database.child("room").child(phone).child("roomrule").child("pet").setValue(pet)
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
            val price = findViewById<EditText>(R.id.editTextNumber2).text.toString()

            val roomInfo = roominfo(Date, time, startpoint, endpoint1, carcard, price)
            database.child("room").child(phone).setValue(roomInfo)
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