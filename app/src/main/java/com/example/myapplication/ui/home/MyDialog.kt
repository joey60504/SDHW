package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.DialogViewBinding
import kotlinx.android.synthetic.main.activity_driver_department_information2.*

class MyDialog(val data:HashMap<*,*>): DialogFragment(){
        //        View元素綁定
        private lateinit var binding: DialogViewBinding
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

//            binding實例化
            binding= DialogViewBinding.inflate(layoutInflater)

//            關閉按鈕
            binding.close.setOnClickListener {
                dismiss()
            }

//            進入按鈕
            binding.access.setOnClickListener {
                Log.d("dialog","hiiiiii")
            }

//駕駛評價            binding.textView76.text=data[""]
            val roominfo =data["roomINFO"] as HashMap<*,*>
            binding.textView84.text=roominfo["date"].toString()
            binding.textView81.text=roominfo["time"].toString()
            binding.textView79.text=roominfo["price"].toString()
            binding.textView78.text=roominfo["other"].toString()
            binding.textView82.text=roominfo["peoplelimit"].toString()
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