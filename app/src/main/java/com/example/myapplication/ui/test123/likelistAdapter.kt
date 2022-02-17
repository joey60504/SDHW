package com.example.myapplication.ui.test123

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.RoomItemBinding
import com.example.myapplication.roominfo
import com.example.myapplication.ui.home.RoomAdapter
import java.lang.Exception


class likelistAdapter(private val itemListener:OnItemClick): RecyclerView.Adapter<likelistAdapter.ViewHolder>() {

    lateinit var dataList:ArrayList<String>
    lateinit var roomlist:HashMap<*,*>
    lateinit var roominfo:HashMap<*,*>
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
        try {
            val roomID = dataList[position]
            val roomUser = roomlist[roomID] as HashMap<*, *>
            roominfo = roomUser["roomINFO"] as HashMap<*, *>
            holder.view.textView4.text=roominfo["date"].toString()
            holder.view.textView20.text=roominfo["time"].toString()
            holder.view.textView19.text=roominfo["number"].toString()
            checkword(roominfo,holder)
            checklikelist(dataList,holder)

        }
        catch (e: Exception){

        }


    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    interface OnItemClick{
        fun onItemClick(position: Int)
        fun likeClick(position: Int)
    }
    fun checkword(roominfo:HashMap<*,*>,holder:ViewHolder){
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
    fun checklikelist(likelist:ArrayList<String>,holder:ViewHolder){
        if(roominfo["driversphone"] in likelist){
            holder.view.heart.setImageResource(R.drawable.heart2)
        }
        else{
            holder.view.heart.setImageResource(R.drawable.addheart_black)
        }
    }
}