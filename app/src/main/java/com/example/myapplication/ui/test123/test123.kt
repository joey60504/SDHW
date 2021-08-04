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


private var _binding: FragmentTest123Binding?=null
private val binding get() = _binding!!

class test123 : Fragment(),likelistAdapter.OnItemClick {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTest123Binding.inflate(inflater, container, false)
        val root: View = binding.root

        binding!!.backLikelist.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }

        binding.recycler4.apply {
            val myAdapter= likelistAdapter(this@test123)
            adapter=myAdapter
            val manager= LinearLayoutManager(requireContext())
            manager.orientation= LinearLayoutManager.VERTICAL
            layoutManager=manager
//            myAdapter.dataList= hashMapOf<*,*>()
        }


        return root


    }

    override fun onItemClick(position: Int) {

    }

}

