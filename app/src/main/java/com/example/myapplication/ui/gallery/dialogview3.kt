package com.example.myapplication.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.DialogViewBinding
import com.example.myapplication.databinding.MyroomdialogviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

lateinit var auth: FirebaseAuth
class dialogview3(val data:String,val roomlist:HashMap<*,*>): DialogFragment() {
    //        View元素綁定
    private lateinit var binding: MyroomdialogviewBinding
    lateinit var roomphone:HashMap<*,*>
    var nowpeoplevalue =0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//            binding實例化
        binding = MyroomdialogviewBinding.inflate(layoutInflater)

//            關閉按鈕
        binding.close.setOnClickListener {
            dismiss()
        }

//      進入按鈕
        roomphone=roomlist[data] as HashMap<*,*>
        val roominfo = roomphone["roomINFO"] as HashMap<*, *>
        binding.access.setOnClickListener {
        }
        binding.textView84.text = roominfo["date"].toString()
        binding.textView81.text = roominfo["time"].toString()
        binding.textView79.text = roominfo["price"].toString()
        binding.textView78.text = roominfo["other"].toString()
        val limitpeople = roominfo["peoplelimit"].toString()
        try {
            val nowpeople = roominfo["roommember"] as ArrayList<String>
            nowpeople.onEach { ele->
                if(ele.isNotBlank()){
                    nowpeoplevalue+=1
                }
            }
        }
        catch(e:Exception){

        }
        binding.textView82.text = "$nowpeoplevalue/$limitpeople"
        binding.textView76.text = roominfo["number"].toString()
        startendpoint(roominfo)
        val roomrule = roomphone["roomRULE"] as HashMap<*, *>
        manwoman = roomrule["gender"].toString()
        pet = roomrule["pet"].toString()
        smoke = roomrule["smoke"].toString()
        child = roomrule["child"].toString()
        iconselect()



        return binding.root
    }

    lateinit var manwoman: String
    lateinit var pet: String
    lateinit var smoke: String
    lateinit var child: String

    fun iconselect() {
        when (manwoman) {
            "限男" -> {
                binding.wm.setImageResource(R.drawable.manonly)
            }
            "限女" -> {
                binding.wm.setImageResource(R.drawable.girlonly)
            }
            "皆可" -> {
                binding.wm.setImageResource(R.drawable.genderisok)
            }
        }
        when (pet) {
            "可" -> {
                binding.pet.setImageResource(R.drawable.ic_pet)
            }
            "不可" -> {
                binding.pet.setImageResource(R.drawable.ic_pet_n)
            }
        }
        when (child) {
            "可" -> {
                binding.child.setImageResource(R.drawable.ic_child)
            }
            "不可" -> {
                binding.child.setImageResource(R.drawable.ic_child_n)
            }
        }
        when (smoke) {
            "可" -> {
                binding.smokeing.setImageResource(R.drawable.ic_smoking)
            }
            "不可" -> {
                binding.smokeing.setImageResource(R.drawable.ic_smoking_n)
            }
        }
    }

    fun startendpoint(roominfo: HashMap<*, *>) {
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