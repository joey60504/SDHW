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
import com.example.myapplication.databinding.FragmentSlideshowBinding
import com.example.myapplication.homepage
import com.example.myapplication.ui.home.RoomAdapter


private var _binding: FragmentSlideshowBinding?=null
private val binding get() = _binding!!
private val dataList=arrayListOf("5顆星","4顆星","3顆星","3顆星","3顆星","3顆星","3顆星")
class SlideshowFragment : Fragment(),slideAdapter.OnItemClick {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding!!.backFriend.setOnClickListener{
            startActivity(Intent(requireContext(), homepage::class.java))
        }

        binding.recycler2.apply {
            val myAdapter= slideAdapter(this@SlideshowFragment)
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