package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.myapplication.R
import com.example.myapplication.RegistrationActivity1
import com.google.firebase.auth.FirebaseAuth

class nav_test : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_nav_test)

        verifyUserIsLogin()
    }

    private fun verifyUserIsLogin(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null){
            val intent = Intent(this, RegistrationActivity1::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            R.id.new_message -> {
                val intent = Intent(this, newmessage::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


