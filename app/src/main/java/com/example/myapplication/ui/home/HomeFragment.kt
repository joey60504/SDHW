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
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.RoomItemBinding
import com.example.myapplication.driver_department_information
import com.example.myapplication.roomrule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_personinformation.*
import java.util.*
import kotlin.coroutines.coroutineContext


class HomeFragment : Fragment(),RoomAdapter.OnItemClick {
    lateinit var auth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var dataList: HashMap<*, *>
    private var currPet: String = " "
    private var currChild: String = " "
    private var currSmoke: String = " "
    private var currGender: String = " "
    private var currDate: String = " "
    private var currTime: String = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        dataselect()
//spinner
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.pet.adapter = MyAdapter(requireContext(), listOf("寵物", "YES", "NO"))
        binding.child.adapter = MyAdapter(requireContext(), listOf("孩童", "YES", "NO"))
        binding.gender.adapter = MyAdapter(requireContext(), listOf("性別", "MAN", "WOMAN", "BOTH"))
        binding.smoke.adapter = MyAdapter(requireContext(), listOf("抽菸", "YES", "NO"))
        binding.date.adapter = MyAdapter(
            requireContext(),
            listOf("出發日", "TODAY", "IN 2 DAYS", "IN 3 DAYS", "IN 5 DAYS")
        )
        binding.time.adapter = MyAdapter(
            requireContext(), listOf(
                "出發時段",
                "0:00~8:00",
                "8:00~9:00",
                "9:00~10:00",
                "10:00~11:00",
                "11:00~12:00",
                "12:00~13:00",
                "13:00~14:00",
                "14:00~15:00",
                "15:00~16:00",
                "16:00~17:00",
                "17:00~18:00",
                "18:00~19:00",
                "19:00~20:00",
                "20:00~21:00",
                "21:00~22:00",
                "22:00~23:00",
                "23:00~0:00"
            )
        )
//spinner完

//無駕照無法點進
        _binding!!.addhouse.setOnClickListener() {
            auth = FirebaseAuth.getInstance()
            var phone = auth.currentUser?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().reference
            database.child("profile").child(phone).get().addOnSuccessListener {
                val user = it.value as HashMap<*, *>
                var photo = user["photo"].toString()
                if (photo == "NO") {
                    Toast.makeText(requireContext(), "無駕照者無法開團", Toast.LENGTH_LONG).show()
                } else {
                    startActivity(
                        Intent(
                            requireContext(),
                            driver_department_information::class.java
                        )
                    )
                }
            }
        }
//無駕照無法點進完

// Spinner item filter取得user選取過濾項目的值
        val pet = arrayListOf(" ","可", "不可")
        val child = arrayListOf(" ","可", "不可")
        val gender = arrayListOf(" ","限男", "限女","皆可")
        val smoke = arrayListOf(" ","可", "不可")
        // Pet
        binding.pet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currPet = pet[position]
                if (position>0) {
                    Toast.makeText(requireActivity(), "你選的是" + pet[position] , Toast.LENGTH_SHORT).show()
                }
                if (binding.recycler1.adapter != null) {
                    dataselect()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.child.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currChild = child[position]
                if (position>0) {
                    Toast.makeText(requireActivity(), "你選的是" + child[position] , Toast.LENGTH_SHORT).show()
                }
                if (binding.recycler1.adapter != null) {
                    dataselect()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currGender = gender[position]
                if (position>0) {
                    Toast.makeText(requireActivity(), "你選的是" + gender[position] , Toast.LENGTH_SHORT).show()
                }
                if (binding.recycler1.adapter != null) {
                    dataselect()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.smoke.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currSmoke = smoke[position]
                if (position>0) {
                    Toast.makeText(requireActivity(), "你選的是" + smoke[position] , Toast.LENGTH_SHORT).show()
                }
                if (binding.recycler1.adapter != null) {
                    dataselect()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        //Spinner item filter取得user選取過濾項目的值 完


        return root

    }



    //    spinner Hint
    class MyAdapter(context: Context, item: List<String>) :
        ArrayAdapter<String>(context, R.layout.simple_spinner_dropdown_item, item) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
            view.setTextColor(Color.WHITE)
            return view
        }

        override fun isEnabled(position: Int): Boolean {
            return position != 0
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
            //set the color of first item in the drop down list to gray
            if (position == 0) {
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
    //likelist資料庫新增
    override fun likeClick(position: Int) {
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val User = it.value as java.util.HashMap<String, Any>

            val (phonenumber, roommap) = roomList[position]
            roommap as HashMap<*, *>
            val roominfo = roommap["roomINFO"] as HashMap<*, *>
            val roomID = roominfo["driversphone"].toString()

            if (User["likelist"] != null) {
                val likelist = User["likelist"] as ArrayList<String>
                if (roomID in likelist) {
                    likelist.remove(roomID)
                    User.put("likelist", likelist)
                    database.child("profile").child(phone).updateChildren(User)
                } else {
                    likelist.add(roomID)
                    User.put("likelist", likelist)
                    database.child("profile").child(phone).updateChildren(User)
                }
            } else {
                User.put("likelist", arrayListOf<String>(roomID))
                database.child("profile").child(phone).updateChildren(User)
            }
        }
    }

    //likelist資料庫新增完

    // Recycler監聽方法完
    lateinit var roomList: List<Pair<*, *>>
    lateinit var profilelist: HashMap<*, *>
    lateinit var roomrule:  HashMap<*,*>

    fun dataselect() {
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root = dataSnapshot.value as HashMap<*, *>
                dataList = root["room"] as HashMap<*, *>
                profilelist = root["profile"] as HashMap<*, *>
                val user = profilelist[auth.currentUser?.phoneNumber.toString()] as HashMap<*, *>
                var likelist = arrayListOf<String>()
                try {
                    likelist = user["likelist"] as ArrayList<String>
                } catch (e: Exception) {

                }

                // 對Datalist以所有的條件做過濾
                var selectedFlag = arrayOf(0 ,0 , 0, 0)  //[0]->Pet [1]-> Child [2]->Gender [3]->Smoke

                if (currPet!=" " )  selectedFlag[0]=1
                if (currChild!=" " )  selectedFlag[1]=1
                if (currGender!=" " )  selectedFlag[2]=1
                if (currSmoke!=" " )  selectedFlag[3]=1

                if (currPet==" " && currChild==" " && currGender==" " && currSmoke==" ") {

                } else{
                    roomList=dataList.toList()

                    loop@ for ( item in roomList){
                        val (phonenumber,roommap)=item
                        roommap as HashMap<*,*>
                        roomrule = roommap["roomRULE"] as HashMap<*,*>
                        Toast.makeText(requireActivity(), "正執行" + phonenumber , Toast.LENGTH_SHORT).show()

                        for ((i,value) in selectedFlag.withIndex()){
                            if (value==1) // 該項user有選
                                when (i) {
                                    0 -> { // 有選Pet
                                        if (currPet != roomrule["pet"].toString())
                                        {
                                            dataList.remove(phonenumber)
                                            Toast.makeText(requireActivity(), "currPet:移除" + phonenumber , Toast.LENGTH_SHORT).show()
                                            continue@loop
                                        }
                                    }
                                    1 -> { //有選child
                                        if (currChild != roomrule["child"].toString())
                                        {
                                            dataList.remove(phonenumber)
                                            Toast.makeText(requireActivity(), "currChild:移除" + phonenumber , Toast.LENGTH_SHORT).show()
                                            continue@loop
                                        }
                                    }
                                    2 -> { //有選gender
                                        if (currGender != roomrule["gender"].toString())
                                        {
                                            dataList.remove(phonenumber)
                                            Toast.makeText(requireActivity(), "currGender:移除" + phonenumber , Toast.LENGTH_SHORT).show()
                                            continue@loop
                                        }
                                    }
                                    3 -> { //有選smoke
                                        if (currSmoke != roomrule["smoke"].toString())
                                        {
                                            dataList.remove(phonenumber)
                                            Toast.makeText(requireActivity(), "currSmoke:移除" + phonenumber , Toast.LENGTH_SHORT).show()
                                            continue@loop
                                        }
                                    }
                                }
                        }
                    }
                }
                // 對Datalist以所有的條件做過濾 完

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
                roomList = dataList.toList()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addValueEventListener(dataListener)
    }

    //dialogview
    override fun onItemClick(position: Int) {
        val (phonenumber, roommap) = roomList[position]
        roommap as HashMap<*, *>
        activity?.supportFragmentManager?.let { MyDialog(roommap).show(it, "myDialog") }
    }
    //dialogview完

}



