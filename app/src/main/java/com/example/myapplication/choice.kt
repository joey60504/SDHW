package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_personinformation.*

class choice : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)
//登入
        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
        val logout=findViewById<Button>(R.id.idLogout)
        if(currentUser==null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        logout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
//完
        firstlogin()
        permission()
    }
//換頁
    fun commute(p0: View){
        startActivity(Intent(this,homepage::class.java))
    }
    fun test(p0: View){
        startActivity(Intent(this,MapsActivity::class.java))
    }

//換頁完
//首次登入跳出
    fun firstlogin(){
       auth = FirebaseAuth.getInstance()
       var phone = auth.currentUser?.phoneNumber.toString()
       var database = FirebaseDatabase.getInstance().reference
       var getdata=object : ValueEventListener {
           override fun onCancelled(error: DatabaseError) {

           }
           override fun onDataChange(p0: DataSnapshot) {
               val res=p0.value as HashMap<*,*>
//               Log.d("login",res.toString())

//               Log.d("login",profile.toString())
               try {
                   val profile= res["profile"] as HashMap<*,*>
                   val userphone=profile[phone] as HashMap<*,*>
                   for(i in userphone.values){
                       if(i=="" || i==null){
                           startActivity(Intent(this@choice,ProfileActivity::class.java))
                       }
                   }
               }
               catch (e:Exception){
                   database.child("profile").child(phone).setValue(User())
                   startActivity(Intent(this@choice,ProfileActivity::class.java))
               }
           }
       }
       database.addValueEventListener(getdata)
    }
    fun permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "無定位功能無法執行程序", Toast.LENGTH_LONG)

                    .show()
            }
        }
    }


//首次登入跳出完

}
