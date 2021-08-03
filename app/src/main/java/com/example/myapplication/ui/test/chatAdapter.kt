package com.example.myapplication.ui.slideshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ChatItemBinding


class chatAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<chatAdapter.ViewHolder>() {

    lateinit var dataList:ArrayList<String>
    private lateinit var binding: ChatItemBinding

    class ViewHolder(val view:ChatItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView30.text=dataList[position]
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