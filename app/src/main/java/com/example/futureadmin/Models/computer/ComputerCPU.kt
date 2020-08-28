package com.example.futureadmin.Models.computer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ComputerCPU(
    var productNumber:String,
    var name : String,
    var category : String,
    var  price : Double,
    var quantity : Int,
    var  imageLink : String,
    var description : String,
    var  CPUSocketType : String,
    var  numberOfCores : Int,
    var  coreClock : Double,
    var  BoostClock : Double,
    var TDP : Int,
    var integratedGraphicsCard : String

    ):
    Parcelable {

    constructor():this("","","",0.0
        ,0,"","","",0,0.0,0.0,0,"")

}