package com.android.firebaseauthdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration1.*

class RegistrationActivity1 : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.`activity_registration1`)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        register()
    }
    private fun register() {
        registerButton.setOnClickListener {

            if (TextUtils.isEmpty(firstnameInput.text.toString())) {
                firstnameInput.setError("請輸入名稱 ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(lastnameInput.text.toString())) {
                firstnameInput.setError("請輸入年齡")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(usernameInput.text.toString())) {
                firstnameInput.setError("請輸入生理性別")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                firstnameInput.setError("請輸入駕照照片URL")
                return@setOnClickListener
            }
        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    val currentUSerDb = databaseReference?.child((currentUser?.uid!!))
                    currentUSerDb?.child("名稱")?.setValue(firstnameInput.text.toString())
                    currentUSerDb?.child("年齡")?.setValue(lastnameInput.text.toString())
                    currentUSerDb?.child("性別")?.setValue(usernameInput.text.toString())
                    currentUSerDb?.child("駕照照片")?.setValue(passwordInput.text.toString())

                    Toast.makeText(this@RegistrationActivity1, "登錄成功", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this@RegistrationActivity1, "登錄失敗 ", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

}