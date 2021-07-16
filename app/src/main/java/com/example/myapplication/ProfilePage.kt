package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_choice.*
import kotlinx.android.synthetic.main.activity_profile_page.*
import java.lang.StringBuilder
import java.util.*

class ProfilePage : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference

        //Get DATA
        var getdata=object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    var name=i.child(phone).child("name").getValue()
                    var age=i.child(phone).child("age").getValue()
                    var gender=i.child(phone).child("gender").getValue()
                    var photo=i.child(phone).child("photo").getValue()
                    var email=i.child(phone).child("email").getValue()
                    pname.text=name.toString()
                    pbirth.text=age.toString()
                    pgender.text=gender.toString()
                    pphoto.text=photo.toString()
                    pemail.text=email.toString()
                    pphone.text=phone
                }
            }

        }
        database.addValueEventListener(getdata)


    }

}
