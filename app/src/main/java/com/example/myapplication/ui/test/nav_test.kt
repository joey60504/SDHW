package com.example.myapplication.ui.test

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentNavTestBinding
import com.example.myapplication.homepage
import com.example.myapplication.ui.slideshow.chatAdapter


private var _binding: FragmentNavTestBinding?=null
private val binding get() = _binding!!
private val dataList=arrayListOf("5顆星","4顆星","3顆星","3顆星","3顆星","3顆星","3顆星")
class nav_test : Fragment(), chatAdapter.OnItemClick{



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavTestBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding!!.backChat.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }
        binding.recycler3.apply {
            val myAdapter= chatAdapter(this@nav_test)
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