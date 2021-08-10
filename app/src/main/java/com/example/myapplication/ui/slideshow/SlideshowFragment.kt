package com.example.myapplication.ui.slideshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.User
import com.example.myapplication.databinding.FragmentSlideshowBinding
import com.example.myapplication.homepage
import com.example.myapplication.ui.gallery.auth
import com.example.myapplication.ui.gallery.myroomAdapter
import com.example.myapplication.ui.home.RoomAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList


private var _binding: FragmentSlideshowBinding?=null
private val binding get() = _binding!!
class SlideshowFragment : Fragment(),slideAdapter.OnItemClick {


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

        return root
    }

    override fun onItemClick(position: Int) {
    }




    lateinit var dataList:HashMap<*,*>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                dataList=root["profile"] as HashMap<*,*>
                //recycler
                activity?.runOnUiThread {
                   binding.recycler2.apply {
                       val myAdapter = slideAdapter(this@SlideshowFragment)
                       adapter = myAdapter
                       val manager = LinearLayoutManager(requireContext())
                       manager.orientation = LinearLayoutManager.VERTICAL
                       layoutManager = manager
                       myAdapter.dataList = dataList
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