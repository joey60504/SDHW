package com.example.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val name : String,val email : String , val age : String,
           val gender : String,val photo : String,val uid : String): Parcelable{
    constructor() : this("", "", "", "", "","")
}
class roominfo(
    val Date: String? ="",
    val time: String? ="",
    val startpoint: String? ="",
    val endpoint1: String? ="",
    val carcard: String? ="",
    val price : String="",
    val number: String = "",
    val peoplelimit: String = "",
    val other: String = "",
    val driversphone: String = "",
    val nolockorlocked :String=""
) {}
class roomrule(
    val gender:String?="",
    val smoke:String?="",
    val child:String?="",
    val pet:String?=""
){}

class pickupinformation(
    val site:String?="",
    val time:String?="",
    val other:String?=""
){}
class usermod(
    val name: String,
    val email:String?=""
){}

class sitearrayinformation(
    val site:String?="",
    val boolean: Boolean
){
    fun to_dict(): Map<String,*> {
        return mapOf("location" to site,"pick" to boolean)

    }
}