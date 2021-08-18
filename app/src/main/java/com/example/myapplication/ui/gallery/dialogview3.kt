package com.example.myapplication.ui.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.databinding.DialogViewBinding
import com.example.myapplication.databinding.MyroomdialogviewBinding
import com.example.myapplication.ui.home.MyDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_choice.*
import java.util.ArrayList

lateinit var auth: FirebaseAuth
class dialogview3(val data1:String,val roomlist:HashMap<*,*>,val usersphone:String): DialogFragment() {
    //        View元素綁定
    private lateinit var binding: MyroomdialogviewBinding
    lateinit var roomphone:HashMap<*,*>
    lateinit var auth: FirebaseAuth
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
        binding.button8.setOnClickListener{
            entergooglemap()
        }

//      進入按鈕
        binding.access.setOnClickListener {
            if(data1 != usersphone) {
                nextpage()
            }
            else{
                Intent(requireContext(), room::class.java).apply {
                    putExtra("Data", this@dialogview3.data1)
                    startActivity(this)
                }
            }
        }

        roomphone=roomlist[data1] as HashMap<*,*>
        val roominfo = roomphone["roomINFO"] as HashMap<*, *>
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
    fun entergooglemap(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root = dataSnapshot.value as HashMap<*, *>
                val room = root["room"] as HashMap<*, *>
                val roomowner=room[data1] as HashMap<*,*>
                val roominfo = roomowner["roomINFO"] as HashMap<*, *>
                val ownerstartpoint = roominfo["startpoint"].toString()
                val ownerendpoint = roominfo["endpoint1"].toString()
                val url = Uri.parse(
                    "https://www.google.com/maps/dir/?api=1&origin=" + ownerstartpoint + "&destination=" + ownerendpoint + "&travelmode=driving"
                )
                val intent = Intent().apply {
                    action = "android.intent.action.VIEW"
                    data = url
                }
                startActivity(intent)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }

    fun nextpage(){
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val phone= auth.currentUser?.phoneNumber.toString()
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root = dataSnapshot.value as HashMap<*, *>
                val profile=root["profile"] as HashMap<*,*>
                val user=profile[phone] as HashMap<*,*>
                if(user["PickupINFO"]!=null) {
                    val pickinfo=user["PickupINFO"] as HashMap<*,*>
                    if(data1 in pickinfo) {
                        Intent(requireContext(), room::class.java).apply {
                            putExtra("Data", this@dialogview3.data1)
                            startActivity(this)
                        }
                    }
                    else{
                        Intent(requireContext(), coustomerINFO::class.java).apply {
                            putExtra("Data", this@dialogview3.data1)
                            startActivity(this)
                        }
                    }
                }
                else {
                    Intent(requireContext(), coustomerINFO::class.java).apply {
                        putExtra("Data", this@dialogview3.data1)
                        startActivity(this)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
}