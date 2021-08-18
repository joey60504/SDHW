package com.example.myapplication.ui.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.MyroomItemBinding
import com.example.myapplication.ui.home.RoomAdapter
import com.example.myapplication.ui.test123.likelistAdapter
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception


class myroomAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<myroomAdapter.ViewHolder>() {
    lateinit var dataList:ArrayList<String>
    lateinit var profilelist:HashMap<*,*>
    lateinit var roominfo:HashMap<*,*>
    lateinit var usersphone:String
    private lateinit var binding: MyroomItemBinding
    class ViewHolder(val view:MyroomItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= MyroomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.cardview11111.setOnClickListener {
            itemListener.onItemClick(position)
        }
        try {
            val room = profilelist["room"] as HashMap<*, *>
            val roomnumber = dataList[position]
            val roomUser = room[roomnumber] as HashMap<*, *>
            roominfo = roomUser["roomINFO"] as HashMap<*, *>
            holder.view.textView26.text=roominfo["date"].toString()
            holder.view.textView27.text=roominfo["time"].toString()
            holder.view.textView25.text=roominfo["number"].toString()
            checkword(roominfo,holder)
            val driversphone=roominfo["driversphone"].toString()
            if(driversphone==usersphone){
                holder.view.textView32.text="您開啟的房間"
            }
        }
        catch (e:Exception){

        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
    fun checkword(roominfo:HashMap<*,*>,holder:ViewHolder){
        try {
            val startpointselect = roominfo["startpoint"].toString()
            val startpointfinal = startpointselect.substring(
                startpointselect.indexOf("區") - 2,
                startpointselect.indexOf("區")
            )
            holder.view.textView28.text = startpointfinal
        }
        catch(e:Exception){

        }
        try {
            val endpointselect = roominfo["endpoint1"].toString()
            val endpointfinal = endpointselect.substring(
                endpointselect.indexOf("區") - 2,
                endpointselect.indexOf("區")
            )
            holder.view.textView29.text = endpointfinal
        }
        catch(e:Exception){

        }
    }
}