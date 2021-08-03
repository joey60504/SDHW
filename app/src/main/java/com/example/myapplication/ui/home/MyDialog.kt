package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.DialogViewBinding

class MyDialog(val position:Int,val data:HashMap<*,*>): DialogFragment(){
        //        View元素綁定
        private lateinit var binding: DialogViewBinding
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

//            binding實例化
            binding= DialogViewBinding.inflate(layoutInflater)

//            關閉按鈕
            binding.close.setOnClickListener {
                dismiss()
            }

//            進入按鈕
            binding.access.setOnClickListener {
                Log.d("dialog","hiiiiii")
            }

            binding.textView76.text=data[position].toString()
//            TODO 實作
            return binding.root

        }

}