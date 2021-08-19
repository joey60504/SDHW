package com.example.myapplication.ui.test123

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.coustomerINFO
import com.example.myapplication.databinding.DialogViewBinding
import com.example.myapplication.room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

lateinit var auth: FirebaseAuth
class Dialogview2(val data1:String,val roomlist:HashMap<*,*>): DialogFragment() {
    //        View元素綁定
    private lateinit var binding: DialogViewBinding
    lateinit var roomphone:HashMap<*,*>
    var nowpeoplevalue =0
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

//      進入按鈕
        roomphone=roomlist[data1] as HashMap<*,*>
        val roominfo = roomphone["roomINFO"] as HashMap<*, *>
        binding.access.setOnClickListener {
            AddMemberInRoomINFO()
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
    fun AddMemberInRoomINFO(){
        val roominfo = roomphone["roomINFO"] as HashMap<*, *>
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        val driversphone = roominfo["driversphone"].toString()
        database.child("room").child(driversphone).get().addOnSuccessListener {
            val User = it.value as java.util.HashMap<String, Any>
            val roominfo = User["roomINFO"] as java.util.HashMap<String, Any>
            val peoplelimit = roominfo["peoplelimit"].toString()
            try {
                if (phone != driversphone) {
                    if (roominfo["nolockorlocked"] == "nolock") {
                        if (roominfo["roommember"] != null) {
                            val roommember = roominfo["roommember"] as ArrayList<String>
                            if (phone in roommember) {
                                Toast.makeText(requireContext(), "您已在該團隊中,請至已加入的房間確認", Toast.LENGTH_LONG).show()
                            } else {
                                if (roommember.size >= peoplelimit.toInt()) {
                                    Toast.makeText(requireContext(), "團隊已滿", Toast.LENGTH_LONG).show()
                                } else {
                                    roommember.add(phone)
                                    roominfo.put("roommember", roommember)
                                    database.child("room").child(driversphone).child("roomINFO").updateChildren(roominfo)
                                    AddRoomNumberInProfile(driversphone)
                                    Intent(requireContext(), coustomerINFO::class.java).apply {
                                        putExtra("Data1", this@Dialogview2.data1)
                                        startActivity(this)
                                        Toast.makeText(
                                            requireContext(), "加入成功,請填寫基本資料", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                        else {
                            roominfo.put("roommember", arrayListOf<String>(phone))
                            database.child("room").child(driversphone).child("roomINFO").updateChildren(roominfo)
                            AddRoomNumberInProfile(driversphone)
                            Intent(requireContext(), coustomerINFO::class.java).apply {
                                putExtra("Data1", this@Dialogview2.data1)
                                startActivity(this)
                                Toast.makeText(requireContext(), "加入成功,請填寫基本資料", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(requireContext(), "團隊已鎖住", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    Toast.makeText(requireContext(), "這是您開啟的房間,請至我的房間確認", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.d("123", e.toString())
            }
        }
    }
    fun AddRoomNumberInProfile(driversphone:String) {
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val User=it.value as java.util.HashMap<String,Any>
            if(User["joining"]!=null){
                val joining =User["joining"] as ArrayList<String>
                if(driversphone in joining){

                }
                else {
                    joining.add(driversphone)
                    User.put("joining", joining)
                    database.child("profile").child(phone).updateChildren(User)
                }
            }
            else{
                User.put("joining", arrayListOf<String>(driversphone))
                database.child("profile").child(phone).updateChildren(User)
            }
        }
    }
}