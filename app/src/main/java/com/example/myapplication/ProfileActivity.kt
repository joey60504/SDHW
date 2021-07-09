package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseRef: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile2)

        auth= FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance()
        val ref = databaseRef?.reference!!.child("profile")

        var currentUser=auth.currentUser

        val addProfileButton  = findViewById<Button>(R.id.addProfileButton)
//       Reference
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

        addProfileButton.setOnClickListener{

            startActivity(Intent(this,AddPersonalDetails::class.java))

        }
        //print phone number
        val sb = StringBuilder()
        sb.append("logged in user phone number :"+ auth.currentUser?.phoneNumber.toString())
        sb.append("\n")

        val tableRef = ref.child(auth.currentUser?.uid!!)
        tableRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                sb.append(snapshot.child("first_name").value.toString())
                sb.append("\n")
                sb.append(snapshot.child("last_name").value.toString())
                sb.append("\n")
                sb.append(snapshot.child("user_name").value.toString())
                sb.append("\n")
                sb.append(snapshot.child("password").value.toString())
                sb.append("\n")

                phoneNumber.text = sb.toString()
            }
        })

    }
    }

