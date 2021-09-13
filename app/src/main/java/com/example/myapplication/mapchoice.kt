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

class mapchoice: AppCompatActivity(),siteadapter.OnItemClick{
    var data1:String=" "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapchoice)
        data1 = intent.getStringExtra("Data").toString()
        dataselect()
        button9.setOnClickListener{
            entermap()
            Toast.makeText(this@mapchoice,"已決定載客順位", Toast.LENGTH_SHORT).show()
        }
        button11.setOnClickListener {
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
                val roomowner = roomlist[phone] as HashMap<*, *>
                val roominfo = roomowner["roomINFO"] as HashMap<*, *>
                try {
                    val roommember = roominfo["roommember"] as ArrayList<String>
                    val sitearray = roominfo["sitearray"] as ArrayList<HashMap<String, Any>>
                    for ((i, e) in sitearray.withIndex()) {
                        e.put("index", i)
                    }
                    runOnUiThread {
                        siterecycler1.apply {
                            val myAdapter = siteadapter(this@mapchoice)
                            adapter = myAdapter
                            val manager = LinearLayoutManager(this@mapchoice)
                            manager.orientation = LinearLayoutManager.VERTICAL
                            layoutManager = manager
                            myAdapter.sitearrayList = sitearray.filter {
                                it["pick"] == false
                            }
                            myAdapter.profilelist = profilelist
                            myAdapter.driversphone = phone
                            myAdapter.roommemberList = roommember
                        }
                        siterecycler2.apply {
                            val myAdapter = siteadapter(this@mapchoice)
                            adapter = myAdapter
                            val manager = LinearLayoutManager(this@mapchoice)
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
                }catch (e:Exception){
                    Toast.makeText(this@mapchoice,"目前無乘客", Toast.LENGTH_LONG).show()
                    entergooglemap("")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }
    override fun onItemClick(position:Int,recyclerposition:Int,pick:Boolean) {
        Log.d("aaa","position:"+position.toString())
        Log.d("aaa","rec:"+recyclerposition.toString())
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("room").child(phone).child("roomINFO").get().addOnSuccessListener {
            if (pick) {
                database.child("room")
                    .child(phone).child("roomINFO").child("sitearray")
                    .child(position.toString()).updateChildren(mapOf(
                        "pick" to false,
                        "order" to null
                    ))
            } else {
                database.child("room")
                    .child(phone).child("roomINFO").child("sitearray")
                    .child(position.toString()).updateChildren(mapOf(
                        "pick" to true,
                        "order" to System.currentTimeMillis()
                    ))
            }
        }
    }



    fun entermap(){
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("room").child(phone).child("roomINFO").get().addOnSuccessListener {
            val roominfo = it.value as java.util.HashMap<String,Any>
            val sitearray=roominfo["sitearray"] as ArrayList<HashMap<*,*>>
            val truesitearrayList =ArrayList<String>().also { arr->
                sitearray.filter { it["pick"]==true }.sortedBy { sort->
                    sort["order"] as Long
                }.onEach { ele->
                    arr.add(ele["location"].toString())
                }
            }

            roominfo.put("truesitearrayList",truesitearrayList)
            database.child("room").child(phone).child("roomINFO").updateChildren(roominfo)
//            for(i in truesitearrayList.indices){
//                val truesite=truesitearrayList[i]
//                val truelocation=truesite["location"].toString()
//                if(roominfo["truesitearrayList"]!=null){
//                    truesitearrayList.add(truelocation)
//                    roominfo.put("truesitearrayList",truesitearrayList)
//                    database.child("room").child(phone).child("roomINFO").updateChildren(roominfo)
//                }
//                else{
//                    roominfo.put("truesitearrayList", arrayListOf<String>(truelocation))
//                    database.child("room").child(phone).child("roomINFO").updateChildren(roominfo)
//                }
//            }
        }
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
            } catch (e: Exception) {
                Toast.makeText(this@mapchoice, "尚未確定載客順序", Toast.LENGTH_SHORT).show()
                val nullsite = ArrayList<String>()
                findsitearraysvalue(nullsite)
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