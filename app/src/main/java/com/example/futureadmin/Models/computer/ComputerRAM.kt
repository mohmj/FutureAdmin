package com.example.futureadmin.Models.computer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ComputerRAM (
    var productNumber:String,
    var name : String,
    var category : String,
    var  price : Double,
    var quantity : Int,
    var  imageLink : String,
    var description : String,
    var typeOfRam : String,
    var pins: Int,
    var speed : Int,
    var numberOfSlice : Int,
    var singleGiga : Int,
    var totalGiga : Int,
    var CASLatency: Int
):
    Parcelable {
    constructor():this( "","","",0.0,0,"",
                        "","",0,0,0,
                        0,0,0)
}