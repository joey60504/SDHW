package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration1.*
import android.text.TextWatcher
import android.util.Log

class RegistrationActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration1)

        firstnameInput.addTextChangedListener(usernamewatcher)
        EmailInput.addTextChangedListener(emailwatcher)

        var database = FirebaseDatabase.getInstance().reference
        registerButton.setOnClickListener() {
            val name = findViewById<EditText>(R.id.firstnameInput).text.toString()
            val email= findViewById<EditText>(R.id.EmailInput).text.toString()
            val age = findViewById<EditText>(R.id.ageInput).text.toString()
            val gender = findViewById<EditText>(R.id.genderinput).text.toString()
            val photo = findViewById<EditText>(R.id.photoinput).text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() && photo.isNotEmpty()) {
                val Users=User(name, email, age, gender, photo)
                database.child(name).setValue(Users)
                    .addOnCompleteListener {
                        Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, choice::class.java))
                    }


            } else {
                Toast.makeText(this, "欄位不可為空", Toast.LENGTH_SHORT).show()
            }


        }

    }

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
            if(text?.length?:0<10){
                firstnameInput.error="至少需要10個字元"
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

}




