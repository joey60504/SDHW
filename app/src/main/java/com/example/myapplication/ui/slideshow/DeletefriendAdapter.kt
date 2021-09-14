package com.example.myapplication.ui.slideshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FriendItemBinding


class DeletefriendAdapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<DeletefriendAdapter.ViewHolder>() {

    lateinit var dataList:ArrayList<String>
    lateinit var profile:HashMap<*,*>
    lateinit var root : HashMap<*,*>
    private lateinit var binding: FriendItemBinding

    class ViewHolder(val view:FriendItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= FriendItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendphone=dataList[position]
        val userphone=profile[friendphone] as HashMap<*,*>
        holder.view.textView2.text=userphone["name"].toString()
        holder.view.imageView6.setImageResource(R.drawable.roomnopeople1)
        holder.view.imageView6.setOnClickListener {
            itemListener.onItemClick(holder,position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClick{
        fun onItemClick(holder: ViewHolder, position: Int)
    }

}