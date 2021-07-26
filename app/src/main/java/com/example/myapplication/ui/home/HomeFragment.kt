package com.example.myapplication.ui.home

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.driver_department_information
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.HashMap


class HomeFragment : Fragment(),RoomAdapter.OnItemClick  {
    lateinit var auth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val dataList=arrayListOf("5顆星","4顆星","3顆星","3顆星","3顆星","3顆星","3顆星")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
//spinner
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.pet.adapter = MyAdapter(requireContext(),listOf("寵物","YES", "NO"))
        binding.child.adapter = MyAdapter(requireContext(),listOf("孩童","YES", "NO"))
        binding.gender.adapter = MyAdapter(requireContext(),listOf("性別","MAN", "WOMAN"))
        binding.smoke.adapter = MyAdapter(requireContext(),listOf("抽菸","YES", "NO"))
        binding.recycler1.apply {
            val myAdapter=RoomAdapter(this@HomeFragment)
            adapter=myAdapter
            val manager=LinearLayoutManager(requireContext())
            manager.orientation=LinearLayoutManager.VERTICAL
            layoutManager=manager
            myAdapter.dataList= dataList
        }
//spinner完
//無駕照無法點進
        _binding!!.addhouse.setOnClickListener(){
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            database.child("profile").child(phone).get().addOnSuccessListener {
                val user =it.value as HashMap<*,*>
                var photo=user["photo"].toString()
                if(photo=="NO"){
                    Toast.makeText(requireContext(),"無駕照者無法開團", Toast.LENGTH_LONG).show()
                }
                else{
                    startActivity(Intent(requireContext(),driver_department_information::class.java))
                }

            }
        }
//無駕照無法點進完



        return root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


//    spinner Hint
    class MyAdapter(context: Context,item:List<String>): ArrayAdapter<String>(context,R.layout.simple_spinner_dropdown_item,item) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
            view.setTextColor(Color.WHITE)
            return view
        }

        override fun isEnabled(position: Int): Boolean {
            return position!=0
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
            //set the color of first item in the drop down list to gray
            if(position == 0) {
                view.setTextColor(Color.GRAY)
            } else {
                //here it is possible to define color for other items by
                //view.setTextColor(Color.RED)
            }
            return view
        }
    }

    override fun onItemClick(position: Int) {
        activity?.supportFragmentManager?.let { MyDialog(position,dataList).show(it,"myDialog") }
    }


}
