package com.example.myapplication.ui.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.MyroomItemBinding
import com.example.myapplication.ui.home.RoomAdapter
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception


class myroomAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<myroomAdapter.ViewHolder>() {
    lateinit var dataList:ArrayList<String>
    lateinit var profilelist:HashMap<*,*>
    lateinit var likelist:ArrayList<String>
    lateinit var roominfo:HashMap<*,*>
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
            holder.view.textView28.text=roominfo["startpoint"].toString()
            holder.view.textView29.text=roominfo["endpoint1"].toString()
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
}