package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration1.*
import java.util.*


class RegistrationActivity1 : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration1)
//資料庫讀取
        auth = FirebaseAuth.getInstance()
        var phone=auth.currentUser?.phoneNumber.toString()
        val registerButton : Button = findViewById(R.id.registerButton)
        var database = FirebaseDatabase.getInstance().reference
        registerButton.setOnClickListener() {
            val name = findViewById<EditText>(R.id.firstnameInput).text.toString()
            val email= findViewById<EditText>(R.id.EmailInput).text.toString()
            val age = findViewById<EditText>(R.id.ageInput).text.toString()
            val gender = findViewById<EditText>(R.id.genderinput).text.toString()
            val photo = findViewById<EditText>(R.id.photoinput).text.toString()
            val uid=phone


            if (name.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() && photo.isNotEmpty()) {
                val Users=User(name, email, age, gender, photo,uid)
                database.child("profile").child(phone).setValue(Users)
                    .addOnCompleteListener {
                        Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, homepage::class.java))
                    }
            } else {
                Toast.makeText(this, "欄位不可為空", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
//監聽
        firstnameInput.addTextChangedListener(usernamewatcher)
        EmailInput.addTextChangedListener(emailwatcher)
    }

    //監聽
    var usernameisvalid=false
    var emailisvalid=false
    fun isValiedEmail(target:CharSequence?):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    val usernamewatcher =object:TextWatcher{
        var b=usernameisvalid
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("signup",text.toString())
            if(text?.length?:0<3){
                firstnameInput.error="請輸入正確格式"
                b=false
            }
            else{
                b=true
            }
        }
        override fun afterTextChanged(s: Editable?) {
            usernameisvalid=b
        }
    }


    val emailwatcher =object:TextWatcher{
        var b =emailisvalid
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if(!isValiedEmail(text)){
                EmailInput.error="請輸入正確EMAIL格式"
                b=false
            }
            else{
                b=true
            }
        }
        override fun afterTextChanged(s: Editable?) {
            emailisvalid=b
        }
    }


    //生日
    fun datePicker(v: View) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        DatePickerDialog(v.context, { view, year, month, day ->
            val monthfix= month+1
            val dateTime = "$year/$monthfix/$day"
            ageInput.setText(dateTime)
        }, year, month, day
        ).show()
    }
}




