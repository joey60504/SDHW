package com.example.myapplication.ui.test123

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentTest123Binding
import com.example.myapplication.homepage
import com.example.myapplication.ui.gallery.auth
import com.example.myapplication.ui.gallery.myroomAdapter
import com.example.myapplication.ui.home.MyDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList


private var _binding: FragmentTest123Binding?=null
private val binding get() = _binding!!

class test123 : Fragment(),likelistAdapter.OnItemClick {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataselect()
        _binding = FragmentTest123Binding.inflate(inflater, container, false)
        val root: View = binding.root

        binding!!.backLikelist.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }
        return root


    }
    override fun likeClick(position: Int) {
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val User=it.value as java.util.HashMap<String,Any>
            database.child("room").child(likelist[position]).get().addOnSuccessListener {
                val roomuser=it.value as HashMap<*,*>
                val roominfo=roomuser["roomINFO"] as HashMap<*,*>
                val driversphone=roominfo["driversphone"].toString()
                if(User["likelist"]!=null){
                    val likelist =User["likelist"] as ArrayList<String>
                    if(driversphone in likelist){
                        likelist.remove(driversphone)
                        User.put("likelist",likelist)
                        database.child("profile").child(phone).updateChildren(User)
                    }
                    else{
                        likelist.add(driversphone)
                        User.put("likelist",likelist)
                        database.child("profile").child(phone).updateChildren(User)
                    }
                }
                else{
                    User.put("likelist", arrayListOf<String>(driversphone))
                    database.child("profile").child(phone).updateChildren(User)
                }
            }
        }
    }
    lateinit var likelist:ArrayList<String>
    lateinit var dataList:HashMap<*,*>
    lateinit var profileList:HashMap<*,*>
    lateinit var roomlist:HashMap<*,*>
    lateinit var user: HashMap<*,*>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                roomlist=root["room"] as HashMap<*,*>
                profileList=root["profile"] as HashMap<*,*>
                user=profileList[auth.currentUser?.phoneNumber.toString()] as HashMap<*,*>
                try {
                    likelist = user["likelist"] as ArrayList<String>
                }
                catch (e:Exception){
                    likelist= arrayListOf()
                }
                activity?.runOnUiThread {
                    binding.recycler4.apply {
                        val myAdapter = likelistAdapter(this@test123)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.dataList = likelist
                        myAdapter.roomlist = roomlist
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }

    override fun onItemClick(position: Int) {
        val LIKELIST=user["likelist"] as ArrayList<String>
        val roommap = LIKELIST[position]
        activity?.supportFragmentManager?.let { Dialogview2(roommap,roomlist,).show(it, "Dialogview2") }
    }

}

