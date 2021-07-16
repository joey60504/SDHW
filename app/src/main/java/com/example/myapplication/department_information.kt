package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.ui.gallery.GalleryFragment

class department_information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_information)
    }
    fun access(p0: View){
        startActivity(Intent(this,room_lock::class.java))
    }
    fun back(p0: View){
        startActivity(Intent(this,GalleryFragment::class.java))
    }
}
