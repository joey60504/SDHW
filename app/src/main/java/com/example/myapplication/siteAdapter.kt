package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.SiteItemBinding
import kotlin.properties.Delegates


class siteadapter(private val itemListener:OnItemClick ): RecyclerView.Adapter<siteadapter.ViewHolder>() {
    var sitearrayList= emptyList<HashMap<*,*>>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    get() {
        return field
    }
    var roommemberList= emptyList<String>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }
    lateinit var profilelist:HashMap<*,*>
    var driversphone=""
        set(value) {
            field=value
            notifyDataSetChanged()
    }
    private lateinit var binding: SiteItemBinding
    class ViewHolder(val view:SiteItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= SiteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.cardviewsite.setOnClickListener {
            itemListener.onItemClick(sitearrayList[position].get("index").toString().toInt(),position,sitearrayList[position].get("pick").toString().toBoolean())
        }
        val roommembersphone=roommemberList[sitearrayList[position].get("index").toString().toInt()]
        val member=profilelist[roommembersphone] as HashMap<*,*>
        val name=member["name"].toString()
        val pickupinfo=member["PickupINFO"] as HashMap<*,*>
        val roompickupinfo=pickupinfo[driversphone] as HashMap<*,*>
        val site=roompickupinfo["site"].toString()
        val time=roompickupinfo["time"].toString()
        holder.view.textViewname.text=name
        holder.view.textView42.text=time
        holder.view.textView43.text=site

    }

    override fun getItemCount(): Int {
        return sitearrayList.size
    }



    interface OnItemClick{
        fun onItemClick(position: Int,recyclerposition:Int,pick:Boolean)
    }
}