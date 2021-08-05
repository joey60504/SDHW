package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.DialogViewBinding
import com.example.myapplication.room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_driver_department_information2.*
import java.util.ArrayList

class MyDialog(val data:HashMap<*,*>): DialogFragment(){
        //        View元素綁定
        private lateinit var binding: DialogViewBinding
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

//            binding實例化
            binding = DialogViewBinding.inflate(layoutInflater)

//            關閉按鈕
            binding.close.setOnClickListener {
                dismiss()
            }

//            進入按鈕
            lateinit var auth: FirebaseAuth
            val roominfo = data["roomINFO"] as HashMap<*, *>
            binding.access.setOnClickListener {
                auth = FirebaseAuth.getInstance()
                var phone = auth.currentUser?.phoneNumber.toString()
                var database = FirebaseDatabase.getInstance().reference
                val driversphone = roominfo["driversphone"].toString()
                val peoplelimit = roominfo["peoplelimit"].toString()
                database.child("room").child(driversphone).get().addOnSuccessListener {
                    val User = it.value as java.util.HashMap<String, Any>
                    try {
                        val roominfo = User["roomINFO"] as java.util.HashMap<String, Any>
                        val compare = roominfo["roommember"] as ArrayList<String>
                        if (roominfo["roommember"] != null) {
                            val roommember = roominfo["roommember"] as ArrayList<String>
                            if (phone in roommember) {
                                Toast.makeText(requireContext(), "您已在該團隊中", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                if (compare.size - 1 >= peoplelimit.toInt()) {
                                    Log.d("123", compare.size.toString())
                                    Toast.makeText(requireContext(), "團隊已滿", Toast.LENGTH_LONG)
                                        .show()
                                } else {
                                    roommember.add(phone)
                                    roominfo.put("roommember", roommember)
                                    database.child("room").child(driversphone).child("roomINFO")
                                        .updateChildren(roominfo)
                                    Toast.makeText(requireContext(), "加入成功", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        } else {
                            roominfo.put("roommember", arrayListOf<String>(phone))
                            database.child("room").child(driversphone).child("roomINFO")
                                .updateChildren(roominfo)
                            Toast.makeText(requireContext(), "加入成功", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Log.d("123", e.toString())
                    }
                }
            }
            binding.textView84.text=roominfo["date"].toString()
            binding.textView81.text=roominfo["time"].toString()
            binding.textView79.text=roominfo["price"].toString()
            binding.textView78.text=roominfo["other"].toString()
            val limitpeople = roominfo["peoplelimit"].toString()
            val nowpeople = roominfo["roommember"] as ArrayList<String>
            val nowpeoplevalue=nowpeople.size-1
            binding.textView82.text="$nowpeoplevalue/$limitpeople"
            binding.textView76.text=roominfo["number"].toString()
            startendpoint(roominfo)

            val roomrule =data["roomRULE"] as HashMap<*,*>
            manwoman =roomrule["gender"].toString()
            pet =roomrule["pet"].toString()
            smoke =roomrule["smoke"].toString()
            child =roomrule["child"].toString()
            iconselect()



            return binding.root
        }
    lateinit var manwoman :String
    lateinit var pet :String
    lateinit var smoke :String
    lateinit var child :String

    fun iconselect(){
        when(manwoman){
            "限男"->{
                binding.wm.setImageResource(R.drawable.manonly)
            }
            "限女"->{
                binding.wm.setImageResource(R.drawable.girlonly)
            }
            "皆可"->{
                binding.wm.setImageResource(R.drawable.genderisok)
            }
        }
        when(pet){
            "可"->{
                binding.pet.setImageResource(R.drawable.ic_pet)
            }
            "不可"->{
                binding.pet.setImageResource(R.drawable.ic_pet_n)
            }
        }
        when(child){
            "可"->{
                binding.child.setImageResource(R.drawable.ic_child)
            }
            "不可"->{
                binding.child.setImageResource(R.drawable.ic_child_n)
            }
        }
        when(smoke){
            "可"->{
                binding.smokeing.setImageResource(R.drawable.ic_smoking)
            }
            "不可"->{
                binding.smokeing.setImageResource(R.drawable.ic_smoking_n)
            }
        }
    }
    fun startendpoint(roominfo:HashMap<*,*>) {
        try {
            val startpointselect = roominfo["startpoint"].toString()
            val startpointfinal = startpointselect.substring(
                startpointselect.indexOf("區") - 2,
                startpointselect.indexOf("區")
            )
            binding.textView85.text = startpointfinal
            val endpointselect = roominfo["endpoint1"].toString()
            val endpointfinal = endpointselect.substring(
                endpointselect.indexOf("區") - 2,
                endpointselect.indexOf("區")
            )
            binding.textView86.text = endpointfinal
        } catch (e: Exception) {

        }
    }
}