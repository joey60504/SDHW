package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mapchoice.*
import kotlinx.android.synthetic.main.activity_mapchoice_for_c.*
import kotlinx.android.synthetic.main.fragment_nav_test.*

class mapchoiceForC : AppCompatActivity(),siteadapter.OnItemClick {
    var data1:String=" "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapchoice_for_c)
        data1 = intent.getStringExtra("Data").toString()
        dataselect()
    }
    fun dataselect() {
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root = dataSnapshot.value as HashMap<*, *>

                val profilelist = root["profile"] as HashMap<*, *>

                val roomlist = root["room"] as HashMap<*, *>
                val roomowner = roomlist[phone] as HashMap<*, *>
                val roominfo = roomowner["roomINFO"] as HashMap<*, *>
                val roommember = roominfo["roommember"] as ArrayList<String>
                val sitearray = roominfo["sitearray"] as ArrayList<HashMap<String,Any>>
                for((i,e) in sitearray.withIndex()) {
                    e.put("index",i)
                }
                runOnUiThread {
                    siterecycler3.apply {
                        val myAdapter = siteadapter(this@mapchoiceForC)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(this@mapchoiceForC)
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.sitearrayList = sitearray.filter {
                            it["pick"] == true
                        }.sortedBy {
                            it["order"] as Long
                        }
                        myAdapter.profilelist = profilelist
                        myAdapter.driversphone = phone
                        myAdapter.roommemberList = roommember
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }
    override fun onItemClick(position: Int, recyclerposition: Int, pick: Boolean) {

    }
}