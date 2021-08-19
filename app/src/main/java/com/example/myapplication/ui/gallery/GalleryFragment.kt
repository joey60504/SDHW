package com.example.myapplication.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGalleryBinding
import com.example.myapplication.homepage
import com.example.myapplication.ui.home.RoomAdapter
import com.example.myapplication.ui.slideshow.slideAdapter
import com.example.myapplication.ui.test123.Dialogview2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

private var _binding: FragmentGalleryBinding?=null
private val binding get() = _binding!!
class GalleryFragment : Fragment(),myroomAdapter.OnItemClick {
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dataselect()
        binding!!.backMyroom.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }
        return root
    }

    override fun onItemClick(position: Int) {
        val roommap = joining[position]
        activity?.supportFragmentManager?.let { dialogview3(roommap,roomlist).show(it, "dialogview3") }
    }


    lateinit var dataList:HashMap<*,*>
    lateinit var profilelist:HashMap<*,*>
    lateinit var user:HashMap<*,*>
    lateinit var roomlist:HashMap<*,*>
    lateinit var joining:ArrayList<String>
    lateinit var usersphone:String
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                profilelist=root["profile"] as HashMap<*,*>
                roomlist=root["room"] as HashMap<*,*>
                usersphone =auth.currentUser?.phoneNumber.toString()
                user=profilelist[usersphone] as HashMap<*,*>
                val myroom=user["MyRoom"].toString()
                try {
                    joining = user["joining"] as ArrayList<String>
                    joining.add(0,myroom)
                }
                catch(e:Exception){
                    joining= arrayListOf(myroom)
                }
                //recycler
                activity?.runOnUiThread {
                    binding.recycler1111.apply {
                        val myAdapter = myroomAdapter(this@GalleryFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.dataList = joining
                        myAdapter.profilelist=root
                        myAdapter.usersphone=usersphone
                    }
                }
                //recyler完
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }
}