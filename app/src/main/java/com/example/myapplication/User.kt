package com.example.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val name : String="",val email : String="" , val age : String="",
           val gender : String="",val photo : String=""): Parcelable{
            constructor() : this("", "", "", "", "")
}
class roominfo(
    val Date: String? ="",
    val time: String? ="",
    val startpoint: String? ="",
    val endpoint1: String? ="",
    val carcard: String? ="",
    val price:String=""){


}