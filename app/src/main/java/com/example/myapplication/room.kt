package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

lateinit var auth: FirebaseAuth
lateinit var data:String
class room : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        dataselect()
    }

    fun dataselect(){
        data = intent.getStringExtra("Data").toString()
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    Log.d("test",data)
                    val root=dataSnapshot.value as HashMap<*,*>
                    val room=root["room"] as HashMap<*,*>
                    val roomowner = room[data] as HashMap<*, *>
                    val roominfo=roomowner["roomINFO"] as HashMap<*,*>
                }
                catch(e:Exception){

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }
}