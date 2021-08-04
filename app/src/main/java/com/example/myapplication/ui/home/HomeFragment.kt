package com.example.myapplication.ui.home

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.User
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.driver_department_information
import com.example.myapplication.homepage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_personinformation.*
import java.util.ArrayList
import kotlin.collections.HashMap


class HomeFragment : Fragment(),RoomAdapter.OnItemClick  {
    lateinit var auth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var dataList : HashMap<*,*>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        dataselect()
//spinner
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.pet.adapter = MyAdapter(requireContext(),listOf("寵物","YES", "NO"))
        binding.child.adapter = MyAdapter(requireContext(),listOf("孩童","YES", "NO"))
        binding.gender.adapter = MyAdapter(requireContext(),listOf("性別","MAN", "WOMAN","BOTH"))
        binding.smoke.adapter = MyAdapter(requireContext(),listOf("抽菸","YES", "NO"))
        binding.date.adapter = MyAdapter(requireContext(),listOf("出發日","TODAY","IN 2 DAYS","IN 3 DAYS","IN 5 DAYS"))
        binding.time.adapter = MyAdapter(requireContext(),listOf("出發時段","0:00~8:00","8:00~9:00","9:00~10:00","10:00~11:00","11:00~12:00",
            "12:00~13:00","13:00~14:00","14:00~15:00","15:00~16:00","16:00~17:00","17:00~18:00","18:00~19:00","19:00~20:00",
            "20:00~21:00","21:00~22:00","22:00~23:00","23:00~0:00"))
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
// spinner HInt完


// Recycler監聽方法
    //dialogview
    lateinit var roomList:List<Pair<*,*>>
    override fun onItemClick(position: Int) {
        val (phonenumber,roommap)=roomList[position]
        roommap as HashMap<*,*>
        activity?.supportFragmentManager?.let { MyDialog(roommap).show(it,"myDialog") }
    }
    //dialogview完
    //likelist資料庫新增
    override fun likeClick(position: Int) {
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val User=it.value as java.util.HashMap<String,Any>

            val (phonenumber,roommap)=roomList[position]
            roommap as HashMap<*,*>
            val roominfo=roommap["roomINFO"] as HashMap<*,*>
            val roomID = roominfo["number"].toString()

            if(User["likelist"]!=null){
                val likelist =User["likelist"] as ArrayList<String>
                if(roomID in likelist){
                    likelist.remove(roomID)
                    User.put("likelist",likelist)
                    database.child("profile").child(phone).updateChildren(User)
                }
                else{
                    likelist.add(roomID)
                    User.put("likelist",likelist)
                    database.child("profile").child(phone).updateChildren(User)
                }
            }
            else{
                User.put("likelist", arrayListOf<String>(roomID))
                database.child("profile").child(phone).updateChildren(User)
            }
        }
    }
    //likelist資料庫新增完
// Recycler監聽方法完


    lateinit var profilelist:HashMap<*,*>
    fun dataselect(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                dataList=root["room"] as HashMap<*,*>

                profilelist=root["profile"] as HashMap<*,*>
                val user=profilelist[auth.currentUser?.phoneNumber.toString()] as HashMap<*,*>
                var likelist= arrayListOf<String>()
                try {
                    likelist = user["likelist"] as ArrayList<String>
                }
                catch(e:Exception){

                }

                //recycler
                activity?.runOnUiThread {
                    binding.recycler1.apply {
                        val myAdapter = RoomAdapter(this@HomeFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.dataList = dataList
                        myAdapter.likelist = likelist
                    }
                }
                //recyler完
                roomList=dataList.toList()
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }


}
