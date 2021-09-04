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
import com.example.myapplication.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_coustomer_info.*
import kotlinx.android.synthetic.main.activity_driver_department_information.*
import java.util.*
import kotlin.collections.HashMap

class coustomerINFO: AppCompatActivity() {
    private var _binding: ActivityCoustomerInfoBinding?=null
    private val binding get() =_binding!!
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityCoustomerInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data1 = intent.getStringExtra("Data1").toString()
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        binding.addpickinfo.setOnClickListener {
            val site =binding.editTextTextPersonName7.text.toString()
            val time =binding.editTextTextPersonName8.text.toString()
            val other =binding.editTextTextPersonName9.text.toString()
            val Pickupinformation = pickupinformation(site,time,other)
            if(site.isNotEmpty() && time.isNotEmpty() && other.isNotEmpty()) {
                addsitetoroominfo(site,data1)
                database.child("profile").child(phone).child("PickupINFO").child(data1)
                    .setValue(Pickupinformation)
                    .addOnCompleteListener {
                        Toast.makeText(this, "加入成功", Toast.LENGTH_LONG).show()
                        Intent(this, room::class.java).apply {
                            putExtra("Data", data1)
                            startActivity(this)
                        }
                        finish()
                    }
            }
            else{
                Toast.makeText(this, "請先填寫完畢", Toast.LENGTH_LONG).show()
            }
        }

    }
    fun customerinfoclose(p0: View){
        startActivity(Intent(this,HomeFragment::class.java))
    }

    fun customerinfoaccess(p0: View){
        startActivity(Intent(this,room::class.java))
    }

    fun timePicker(v:View){
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR]
        val minute = calendar[Calendar.MINUTE]
        TimePickerDialog(v.context, {_, hour, minute ->
            if(minute<10){
                val dateTime= "$hour:0$minute"
                binding.editTextTextPersonName8.setText(dateTime)
            }
            else{
                val dateTime= "$hour:$minute"
                binding.editTextTextPersonName8.setText(dateTime)
            }
        },hour,minute,true
        ).show()
    }


    fun addsitetoroominfo(site:String,data1:String){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val sitearrayinformation = sitearrayinformation(site,false).to_dict()
        database.child("room").child(data1).get().addOnSuccessListener {
            try {
                val roomowner = it.value as java.util.HashMap<String, Any>
                val roominfo = roomowner["roomINFO"] as java.util.HashMap<String, Any>
                if (roominfo["sitearray"] != null) {
                    val sitearray = roominfo["sitearray"] as ArrayList<Map<String,*>>
                    sitearray.add(sitearrayinformation)
                    roominfo.put("sitearray", sitearray)
                    database.child("room").child(data1).child("roomINFO")
                        .updateChildren(roominfo)
                }
                else {
                    roominfo.put("sitearray", arrayListOf<Map<String,*>>(sitearrayinformation))
                    database.child("room").child(data1).child("roomINFO").updateChildren(roominfo)
                }
            }
            catch (e: Exception) {
                Log.d("123", e.toString())
            }
        }
    }
}


