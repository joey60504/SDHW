package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mapchoice.*

class mapchoice : AppCompatActivity(),siteadapter.OnItemClick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapchoice)
        dataselect()
    }
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>

                val profilelist=root["profile"] as HashMap<*,*>

                val roomlist=root["room"] as HashMap<*,*>
                val roomowner=roomlist[phone] as HashMap<*,*>
                val roominfo=roomowner["roomINFO"] as HashMap<*,*>
                val roommember=roominfo["roommember"] as ArrayList<String>

                runOnUiThread {
                    siterecycler1.apply {
                        val myAdapter = siteadapter(this@mapchoice)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(this@mapchoice)
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.roommemberList = roommember
                        myAdapter.profilelist=profilelist
                    }
                    siterecycler2.apply {
                        val myAdapter = siteadapter(this@mapchoice)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(this@mapchoice)
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }
    override fun onItemClick(position: Int) {

    }
}