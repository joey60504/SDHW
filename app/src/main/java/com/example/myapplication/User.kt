package com.example.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val UID : String,val email : String , val age : String,
           val gender : String,val photo : String,val name : String): Parcelable{
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
    val driversphone: String = ""
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