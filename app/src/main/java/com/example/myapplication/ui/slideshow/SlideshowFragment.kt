package com.example.myapplication.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentSlideshowBinding
import com.example.myapplication.ui.gallery.auth
import com.example.myapplication.ui.gallery.myroomAdapter
import com.example.myapplication.ui.home.RoomAdapter
import com.example.myapplication.ui.test.chatAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList


private var _binding: FragmentSlideshowBinding?=null
private val binding get() = _binding!!
class SlideshowFragment : Fragment(),slideAdapter.OnItemClick {

    var chatusers: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataselect()
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding!!.backFriend.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }
        binding.button12.setOnClickListener{
            startActivity(Intent(requireContext(), Deletefriend::class.java))
        }
        return root
    }

    lateinit var dataList:HashMap<*,*>
    lateinit var friendlist:ArrayList<String>
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
                activity?.runOnUiThread {
                    binding.recycler2.apply {
                        val myAdapter = slideAdapter(this@SlideshowFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
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


    override fun onItemClick(holder: slideAdapter.ViewHolder, position: Int) {

  //      val datacheckList = dataList.keys.toList()
        val friendname=friendlist[position].toString()

        val ref =FirebaseDatabase.getInstance().getReference("/profile/$friendname")//$userphonenumber
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatusers = p0.getValue(User::class.java)
                val intent = Intent(context, chatroom1::class.java)
                intent.putExtra(newmessage.USER_KEY, chatusers)
                startActivity(intent)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}






