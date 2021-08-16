package com.example.myapplication

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityCoustomerInfoBinding
import kotlinx.android.synthetic.main.activity_coustomer_info.*
import kotlinx.android.synthetic.main.activity_driver_department_information.*
import java.util.*

class coustomerINFO: AppCompatActivity() {
    private var _binding: ActivityCoustomerInfoBinding?=null
    private val binding get() =_binding!!
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityCoustomerInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra("Data").toString()
        Log.d("dataa",data)
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        binding.addpickinfo.setOnClickListener {
            val site =binding.editTextTextPersonName7.text.toString()
            val time =binding.editTextTextPersonName8.text.toString()
            val other =binding.editTextTextPersonName9.text.toString()
            val Pickupinformation = pickupinformation(site,time,other)
            if(site.isNotEmpty() && time.isNotEmpty() && other.isNotEmpty()) {
                database.child("profile").child(phone).child("PickupINFO").child(data)
                    .setValue(Pickupinformation)
                    .addOnCompleteListener {
                        Toast.makeText(this, "加入成功,跳轉至房間頁面", Toast.LENGTH_LONG).show()
                        Intent(this, room::class.java).apply {
                            putExtra("Data", data)
                            startActivity(this)
                        }
                    }
            }
            else{
                Toast.makeText(this, "請先填寫完畢", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun timePicker(v: View){
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR]
        val minute = calendar[Calendar.MINUTE]
        TimePickerDialog(v.context, {_, hour, minute ->
            if(minute<10){
                val dateTime= "$hour:0$minute"
                editTextDate2.setText(dateTime)
            }
            else{
                val dateTime= "$hour:$minute"
                editTextTextPersonName8.setText(dateTime)
            }
        },hour,minute,true
        ).show()
    }
}


