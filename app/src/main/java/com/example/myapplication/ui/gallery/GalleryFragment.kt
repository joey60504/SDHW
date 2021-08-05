package com.example.myapplication.ui.gallery

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGalleryBinding
import com.example.myapplication.homepage
import com.example.myapplication.ui.slideshow.slideAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
private val dataList=arrayListOf("5顆星","4顆星","3顆星","3顆星","3顆星","3顆星","3顆星")
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

        binding!!.backMyroom.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }

        binding.recycler1111.apply {
            val myAdapter= myroomAdapter(this@GalleryFragment)
            adapter=myAdapter
            val manager= LinearLayoutManager(requireContext())
            manager.orientation= LinearLayoutManager.VERTICAL
            layoutManager=manager
            myAdapter.dataList= dataList
        }

        return root
    }

    override fun onItemClick(position: Int) {
    }
}