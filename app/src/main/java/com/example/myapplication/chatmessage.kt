package com.example.myapplication

class chatmessage (val id: String,val text: String,val fromid: String,val toid: String,
                   val phone: String){
    constructor() : this("", "", "", "", "")
}