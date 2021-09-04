package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        button2.setOnClickListener {
            datasel()
        }
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
                val roomowner = roomlist[data1] as HashMap<*, *>
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
                        myAdapter.driversphone = data1
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
    fun findsitearraysvalue(sitearray: java.util.ArrayList<String>){
        var site0=""
        var finsite=""
        for (i in sitearray.indices) {

            Log.d("77777",sitearray[i]+"%7C")

            var site=sitearray[i]+"%7c"
            Log.d("000", site)
            site0+=site
        }
        finsite=site0
        entergooglemap(finsite)
    }
    lateinit var profilelist:HashMap<*,*>
    lateinit var roominfo:HashMap<*,*>
    lateinit var roomlist:HashMap<*,*>
    fun datasel() {
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference

        database.child("room").child(data1).child("roomINFO").get().addOnSuccessListener {
            val roominfo=it.value as HashMap<*,*>
            try {
                var site = roominfo["truesitearrayList"] as ArrayList<String>
                findsitearraysvalue(site)
                if(roominfo["truesitearrayList"]==null){
                    Toast.makeText(this@mapchoiceForC, "尚未確定載客順序", Toast.LENGTH_SHORT).show()
                    val nullsite = ArrayList<String>()
                    findsitearraysvalue(nullsite)
                }
            } catch (e: Exception) {
            }

        }
    }

    fun entergooglemap(site: String){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        database.child("room").child(data1).child("roomINFO").get().addOnSuccessListener {
            val roominfo = it.value as HashMap<*, *>
            val ownerstartpoint = roominfo["startpoint"].toString()
            val ownerendpoint = roominfo["endpoint1"].toString()
            val url = Uri.parse(
                "https://www.google.com/maps/dir/?api=1&origin=" + ownerstartpoint + "&destination=" + ownerendpoint + "&travelmode=driving&waypoints="+site
            )
            Log.d("00000",url.toString())
            val intent = Intent().apply {
                action = "android.intent.action.VIEW"
                data = url
            }
            startActivity(intent)
        }
    }
}