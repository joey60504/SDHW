package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.chatmessage
import com.example.myapplication.ui.home.RoomAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chatroom1.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*


class chatroom1 : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    companion object {
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom1)

        recyclerview_chatroom1.adapter = adapter

        toUser = intent.getParcelableExtra<User>(newmessage.USER_KEY)

        textView61.text = toUser?.name

        listenformessage()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message...")
            performSendMessage()
        }
    }

    private fun listenformessage() {
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        val toid = toUser!!.name
        database.child("profile").child(phone).get().addOnSuccessListener {

            val user = it.value as HashMap<*, *>
            val fromid = user["name"].toString()

            val ref = FirebaseDatabase.getInstance().getReference("/user-message/$fromid/$toid")



            ref.addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val chatMessage = p0.getValue(chatmessage::class.java)

                    if (chatMessage != null) {
                        Log.d(TAG, chatMessage.text)
                        if (fromid == chatMessage.fromid) {
                            adapter.add(ChatFromItem(chatMessage.text))
                        } else {
                            adapter.add(ChatToItem(chatMessage.text, toUser!!))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }
            })
        }
    }



    private fun performSendMessage() {
        //send message to firebase
        auth = FirebaseAuth.getInstance()
        var phone = auth.currentUser?.phoneNumber.toString()
        var database = FirebaseDatabase.getInstance().reference
        database.child("profile").child(phone).get().addOnSuccessListener {
            val user = it.value as HashMap<*, *>
            val text = editTextTextMultiLine2.text.toString()

            val fromid = toUser!!.name

            val toid = user["name"].toString()

            if (toid != "") {
                Log.d(TAG, fromid.toString())
                Log.d(TAG, toid)
            } else {
                Log.d(TAG, "error")
            }

            //if (fromid == null) return

            val reference =
                FirebaseDatabase.getInstance().getReference("/user-message/$fromid/$toid")
                    .push()

            val toreference =
                FirebaseDatabase.getInstance().getReference("/user-message/$toid/$fromid")
                    .push()

            val chatMessage = chatmessage(
                reference.key!!, text, fromid!!, toid,
                System.currentTimeMillis() / 1000
            )
            reference.setValue(chatMessage).addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
                editTextTextMultiLine2.text.clear()
                recyclerview_chatroom1.scrollToPosition(adapter.itemCount - 1)
            }

            toreference.setValue(chatMessage)

            val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-message/$fromid/$toid")
            latestMessageRef.setValue(chatMessage)

            val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-message/$toid/$fromid")
            latestMessageToRef.setValue(chatMessage)
        }
    }

    fun back5(p0: View) {
        startActivity(Intent(this, newmessage::class.java))
    }

}

class ChatFromItem(val text: String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.username_textmessage.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}


class ChatToItem(val text: String, val user: User) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}