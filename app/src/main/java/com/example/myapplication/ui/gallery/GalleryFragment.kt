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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

lateinit var auth: FirebaseAuth
private var _binding: FragmentGalleryBinding?=null
private val binding get() = _binding!!
class GalleryFragment : Fragment(),myroomAdapter.OnItemClick {


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
    }



    lateinit var dataList:HashMap<*,*>
    lateinit var profilelist:HashMap<*,*>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                profilelist=root["profile"] as HashMap<*,*>
                val user=profilelist[auth.currentUser?.phoneNumber.toString()] as HashMap<*,*>
                val myroom=user["MyRoom"].toString()
                var likelist= arrayListOf<String>()
                var joining:ArrayList<String>
                try {
                    joining = user["joining"] as ArrayList<String>
                    joining.add(0,myroom)
                    likelist = user["likelist"] as ArrayList<String>
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
                        myAdapter.likelist = likelist
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