package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityModifypersoninformationBinding
import com.example.myapplication.databinding.ManagerBinding
import com.example.myapplication.ui.personinformation.personinformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_modifypersoninformation.*
import kotlinx.android.synthetic.main.activity_modifypersoninformation.textView35
import kotlinx.android.synthetic.main.activity_modifypersoninformation.textView36
import kotlinx.android.synthetic.main.activity_modifypersoninformation.textView38
import kotlinx.android.synthetic.main.fragment_personinformation.*

class modifypersoninformation : AppCompatActivity() {
    lateinit var binding: ActivityModifypersoninformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityModifypersoninformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getolddata()
        button7.setOnClickListener{
            getnewdata()
        }
    }
    fun getolddata(){
        auth = FirebaseAuth.getInstance()
        val phone=auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val profile=it.value as java.util.HashMap<*,*>
            val age=profile["age"].toString()
            val gender=profile["gender"].toString()
            val photo=profile["photo"].toString()
            binding.textView35.text=age
            binding.textView36.text=gender
            binding.textView38.text=photo
            binding.textview41.text=phone
        }
    }
    fun getnewdata(){
        auth = FirebaseAuth.getInstance()
        var phone=auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        val name=findViewById<EditText>(R.id.editTextTextPersonName11).text.toString()
        val email=findViewById<EditText>(R.id.editTextTextPersonName12).text.toString()
        if (name.isNotEmpty() && email.isNotEmpty()) {
            database.child("profile").child(phone).child("name").setValue(name)
            database.child("profile").child(phone).child("email").setValue(email)
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, homepage::class.java))
        }
        else {
            Toast.makeText(this, "欄位不可為空", Toast.LENGTH_SHORT).show()
        }
    }
}