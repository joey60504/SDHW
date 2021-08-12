package com.example.myapplication.ui.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ChatItemBinding


class chatAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<chatAdapter.ViewHolder>() {

    lateinit var dataList:HashMap<*,*>
    lateinit var root : HashMap<*,*>
    private lateinit var binding: ChatItemBinding

    class ViewHolder(val view:ChatItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendList=dataList.keys.toList()
        val friendname=friendList[position]
        holder.view.textView30.text=friendname.toString()

        val friendchatinfo=dataList[friendname] as HashMap<*,*>
        val textvalue=friendchatinfo["text"].toString()
        holder.view.textView37.text=textvalue

        holder.view.cardview2.setOnClickListener {
            itemListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}