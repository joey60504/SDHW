package com.example.myapplication.ui.manager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ManagerBinding
import com.example.myapplication.ui.gallery.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class manager: AppCompatActivity(), managerAdapter.OnItemClick {
    lateinit var binding: ManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataselect()
    }
    lateinit var dataList:HashMap<*,*>
    lateinit var arrprofilelist :ArrayList<String>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        var phone = com.example.myapplication.auth.currentUser?.phoneNumber.toString()
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                dataList=root["profile"] as HashMap<*,*>
                val profilelist=dataList.keys.toList()
                try {
                    arrprofilelist = profilelist as ArrayList<String>
                    arrprofilelist.remove(phone)
                }
                catch(e:Exception){
                    arrprofilelist= arrayListOf()
                }
                //recycler
                runOnUiThread {
                    binding.managerrecycler.apply {
                        val myAdapter = managerAdapter(this@manager)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(this@manager)
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.dataList = dataList
                        myAdapter.profilelist=arrprofilelist
                    }
                }
                //recyler完
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }

    override fun onItemClick(holder: managerAdapter.ViewHolder, position: Int) {
        AlertDialog.Builder(this).apply {
            setTitle("提醒")
            setMessage("確定要刪除此使用者??刪除後無法修改")
            setPositiveButton("確定?") { _, _ ->
                deleteuserdata(position)
            }
            setNegativeButton("取消", null)
        }.show()
    }

    lateinit var userssite:String
    fun deleteuserdata(position: Int){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val userphone=arrprofilelist[position]
        //刪除乘客的profile資訊
        database.child("room").child(userphone).child("roomINFO").get().addOnSuccessListener {
            val roominfoleave = it.value as java.util.HashMap<String, Any>
            if(roominfoleave["roommember"]!=null) {
                val roommember = roominfoleave["roommember"] as ArrayList<String>
                for (i in roommember.indices) {
                    val coustomersphone = roommember[i]
                    database.child("profile").child(coustomersphone).get().addOnSuccessListener {
                        val userinfoleave = it.value as java.util.HashMap<String, Any>

                        val joining = userinfoleave["joining"] as ArrayList<String>
                        joining.remove(userphone)
                        userinfoleave.put("joining", joining)
                        database.child("profile").child(coustomersphone).updateChildren(userinfoleave)

                        val PickupINFO = userinfoleave["PickupINFO"] as HashMap<String, Any>
                        val driversphoneinPickupINFO = PickupINFO[userphone] as HashMap<*, *>
                        userssite = driversphoneinPickupINFO["site"].toString()
                        val pickinfoDriversphone = PickupINFO[userphone] as HashMap<*, *>
                        pickinfoDriversphone.keys.remove("site")
                        pickinfoDriversphone.keys.remove("time")
                        pickinfoDriversphone.keys.remove("other")
                        database.child("profile").child(coustomersphone).child("PickupINFO").updateChildren(PickupINFO)
                    }
                }
            }  //刪除好友
        }.continueWith{
            database.child("profile").child(userphone).get().addOnSuccessListener {
                val user=it.value as java.util.HashMap<String,Any>
                if(user["friendlist"]!=null) {
                    val friendlist = user["friendlist"] as ArrayList<String>
                    for (i in friendlist.indices) {
                        val friendsphone = friendlist[i].toString()
                        database.child("profile").child(friendsphone).get().addOnSuccessListener {
                            val userinfo = it.value as java.util.HashMap<String, Any>
                            val friendlist = userinfo["friendlist"] as ArrayList<String>
                            friendlist.remove(userphone)
                            userinfo.put("friendlist",friendlist)
                            database.child("profile").child(friendsphone).updateChildren(userinfo)
                        }
                    }
                }
            }
        }
        //刪除搭乘的車的room資訊
        database.child("profile").child(userphone).get().addOnSuccessListener {
            val user=it.value as java.util.HashMap<String,Any>
            if(user["joining"]!=null) {
                val joininglist = user["joining"] as ArrayList<String>
                for (i in joininglist.indices) {
                    val driversphone = joininglist[i].toString()
                    database.child("profile").child(userphone).get().addOnSuccessListener {
                        val userinfoleave = it.value as java.util.HashMap<String, Any>
                        val PickupINFO = userinfoleave["PickupINFO"] as HashMap<String, Any>
                        val driversphoneinPickupINFO = PickupINFO[driversphone] as HashMap<*, *>
                        userssite = driversphoneinPickupINFO["site"].toString()
                    }.continueWith {
                        database.child("room").child(driversphone).child("roomINFO").get()
                            .addOnSuccessListener {
                                val roominfoleave = it.value as java.util.HashMap<String, Any>
                                if(roominfoleave["membeready"]!=null) {
                                    val membeready = roominfoleave["membeready"] as HashMap<*, *>
                                    membeready.keys.remove(userphone)
                                    database.child("room").child(driversphone).child("roomINFO")
                                        .updateChildren(roominfoleave)
                                }
                                val roommember1 = roominfoleave["roommember"] as java.util.ArrayList<String>
                                val indexofuser = roommember1.indexOf(userphone)
                                val sitearray = roominfoleave["sitearray"] as java.util.ArrayList<String>
                                sitearray.removeAt(indexofuser)
                                roominfoleave.put("sitearray", sitearray)

                                val roommember2 =
                                    roominfoleave["roommember"] as java.util.ArrayList<String>
                                roommember2.remove(userphone)
                                roominfoleave.put("roommember", roommember2)
                                try {
                                    val truesitearrayList =
                                        roominfoleave["truesitearrayList"] as java.util.ArrayList<String>
                                    truesitearrayList.remove(userssite)
                                    roominfoleave.put("truesitearrayList", truesitearrayList)
                                } catch (e: Exception) {
                                }
                                database.child("room").child(driversphone).child("roomINFO")
                                    .updateChildren(roominfoleave)
                            }
                    }
                }
            }
        }
        //刪除自身profile資訊
        database.get().addOnSuccessListener {
            val root=it.value as java.util.HashMap<String,Any>
            val profile=root["profile"] as HashMap<*,*>
            profile.keys.remove(userphone)
            database.updateChildren(root)
        }
        //刪除自身room資訊
        database.child("room").get().addOnSuccessListener {
            val room = it.value as java.util.HashMap<String, Any>
            val roomowner = room[userphone] as HashMap<*, *>
            roomowner.keys.remove("roomINFO")
            roomowner.keys.remove("roomRULE")
            database.child("room").updateChildren(room)
        }
    }
}

