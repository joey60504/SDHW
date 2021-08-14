package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_newmessage.*
import kotlinx.android.synthetic.main.chat_item.view.*

class newmessage : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newmessage)

        recyclerview_newmessage.adapter = adapter

        supportActionBar?.title = "Select User"

        val adapter = GroupAdapter<GroupieViewHolder>()
        recyclerview_newmessage.adapter = adapter
        fetchUsers()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }


    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/profile")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()

                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem
                    val intent = Intent(view.context, chatroom1::class.java)
                    intent.putExtra(USER_KEY, userItem.user.name)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                }
                recyclerview_newmessage.adapter = adapter
            }


            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    val adapter = GroupAdapter<GroupieViewHolder>()

}

// new message import name
class UserItem(val user: User): Item<GroupieViewHolder>(){

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView30.text = user.name
    }

    override fun getLayout(): Int {
        return R.layout.chat_item
    }

}