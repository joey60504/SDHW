package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_singup.*

class singup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)
        editTextTextPersonName.addTextChangedListener(usernamewatcher)
        editTextTextPersonName2.addTextChangedListener(emailwatcher)
        editTextTextPassword.addTextChangedListener(passwordwatcher)
        editTextTextPassword2.addTextChangedListener(passwordconfirm)
        findViewById<Button>(R.id.button1).isEnabled=false
        button.setOnClickListener {
            if(usernameisvalid and emailisvalid and passwordisvalid and passwordconfirmisvalid) {
                findViewById<Button>(R.id.button1).isEnabled=true
                Toast.makeText(this,"請點選註冊",Toast.LENGTH_SHORT).show()
            }
            else{
                findViewById<Button>(R.id.button1).isEnabled=false
                Toast.makeText(this,"請正確完成資料",Toast.LENGTH_SHORT).show()
            }
        }
        button1.setOnClickListener {
            Toast.makeText(this,"註冊成功",Toast.LENGTH_SHORT).show()
        }
    }
    var usernameisvalid=false
    var emailisvalid=false
    var passwordisvalid=false
    var passwordconfirmisvalid=false

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
                editTextTextPersonName.error="至少需要10個字元"
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
                editTextTextPersonName2.error="請輸入正確EMAIL格式"
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

    val passwordwatcher =object:TextWatcher{
        var b=passwordisvalid
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("signup",text.toString())
            if(text?.length?:0<8){
                editTextTextPassword.error="至少需要8個字元"
                b=false
            }
            else{
                b=true
            }
        }
        override fun afterTextChanged(s: Editable?) {
            passwordisvalid=b
        }
    }
    val passwordconfirm =object:TextWatcher{
        var b=passwordconfirmisvalid
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if(editTextTextPassword.text.toString()!=editTextTextPassword2.text.toString()){
                editTextTextPassword2.error="密碼不一致"
                b=false
            }
            else{
                b=true
            }
        }
        override fun afterTextChanged(s: Editable?) {
            passwordconfirmisvalid=b
        }
    }
}