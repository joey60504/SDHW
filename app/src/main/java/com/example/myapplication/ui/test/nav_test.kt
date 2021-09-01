package com.example.myapplication.ui.test

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentNavTestBinding
import com.example.myapplication.ui.gallery.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_newmessage.*


private var _binding: FragmentNavTestBinding?=null
private val binding get() = _binding!!
class nav_test : Fragment(), chatAdapter.OnItemClick{



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavTestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding!!.backChat.setOnClickListener {
            startActivity(Intent(requireContext(), homepage::class.java))
        }

        binding!!.addMessage.setOnClickListener {
            startActivity(Intent(requireContext(), newmessage::class.java))
        }

        dataselect()
        return root

    }

    lateinit var dataList:HashMap<*,*>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                val profile=root["profile"] as HashMap<*,*>
                val phone=profile[auth.currentUser?.phoneNumber.toString()] as HashMap<*,*>
                val username=phone["name"].toString()
                dataList=root["latest-message"] as HashMap<*,*>
                try {
                    val friendlist = dataList[username] as HashMap<*,*>
                    activity?.runOnUiThread {
                        binding.recycler3.apply {
                            val myAdapter = chatAdapter(this@nav_test)
                            adapter = myAdapter
                            val manager = LinearLayoutManager(requireContext())
                            manager.orientation = LinearLayoutManager.VERTICAL
                            layoutManager = manager
                            myAdapter.dataList = friendlist!!
                            myAdapter.root = root
                        }
                    }
                }
                catch (e:Exception){

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