package com.example.myapplication.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.RoomItemBinding
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log


class RoomAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<RoomAdapter.ViewHolder>() {
    lateinit var dataList:HashMap<*,*>
    private lateinit var binding: RoomItemBinding

    class ViewHolder(val view:RoomItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= RoomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.cardView.setOnClickListener {
            itemListener.onItemClick(position)
        }
        holder.view.heart.setOnClickListener{
            itemListener.likeClick(position)
        }
        val roomList=dataList.toList()
        val (phonenumber,roommap)=roomList[position]
        roommap as HashMap<*,*>
        val roominfo = roommap["roomINFO"] as HashMap<*,*>
        holder.view.textView4.text=roominfo["date"].toString()
        holder.view.textView20.text=roominfo["time"].toString()
        holder.view.textView19.text=roominfo["number"].toString()
        try {
            val startpointselect = roominfo["startpoint"].toString()
            val startpointfinal = startpointselect.substring(
                startpointselect.indexOf("區") - 2,
                startpointselect.indexOf("區")
            )
            holder.view.textView9.text = startpointfinal
        }
        catch(e:Exception){

        }
        try {
            val endpointselect = roominfo["endpoint1"].toString()
            val endpointfinal = endpointselect.substring(
                endpointselect.indexOf("區") - 2,
                endpointselect.indexOf("區")
            )
            holder.view.textView11.text = endpointfinal
        }
        catch(e:Exception){

        }




    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
        fun likeClick(position: Int)
    }
}