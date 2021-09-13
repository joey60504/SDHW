package com.example.myapplication.ui.slideshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityRoomBinding
import com.example.myapplication.databinding.DeletefriendBinding
import com.example.myapplication.homepage
import com.example.myapplication.ui.gallery.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class Deletefriend : AppCompatActivity(),DeletefriendAdapter.OnItemClick {
    lateinit var binding: DeletefriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DeletefriendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataselect()
    }



    lateinit var dataList:HashMap<*,*>
    lateinit var friendlist: ArrayList<String>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                val profile=root["profile"] as HashMap<*,*>
                val phone=profile[auth.currentUser?.phoneNumber.toString()] as HashMap<*,*>
                try {
                    friendlist = phone["friendlist"] as ArrayList<String>
                }
                catch (e:Exception){
                    friendlist = arrayListOf()
                }
                dataList=root["profile"] as HashMap<*,*>
                //recycler
                runOnUiThread {
                    binding.delfriendrecycler.apply {
                        val myAdapter = DeletefriendAdapter(this@Deletefriend)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(this@Deletefriend)
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.dataList = friendlist
                        myAdapter.profile  = profile
                    }
                }
                //recyler完
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
    override fun onItemClick(holder: DeletefriendAdapter.ViewHolder, position: Int) {
        AlertDialog.Builder(this).apply {
            setTitle("提醒")
            setMessage("確定要刪除此好友?刪除後無法修改")
            setPositiveButton("確定?") { _, _ ->
                deletefriend(position)
            }
            setNegativeButton("取消", null)
        }.show()
    }
    fun deletefriend(position:Int){
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        val friendsphone=friendlist[position]
        database.child("profile").child(phone).get().addOnSuccessListener {
            val user=it.value as java.util.HashMap<String,Any>
            val myfriendlist =user["friendlist"] as ArrayList<String>
            myfriendlist.remove(friendsphone)
            user.put("friendlist", myfriendlist)
            database.child("profile").child(phone).updateChildren(user)
        }
        database.child("profile").child(friendsphone).get().addOnSuccessListener {
            val user=it.value as java.util.HashMap<String,Any>
            val tofriendlist =user["friendlist"] as ArrayList<String>
            tofriendlist.remove(phone)
            user.put("friendlist", tofriendlist)
            database.child("profile").child(friendsphone).updateChildren(user)
        }
    }
}