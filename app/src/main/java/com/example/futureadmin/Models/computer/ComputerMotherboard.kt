package com.example.futureadmin.Models.computer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ComputerMotherboard(
    var productNumbrer:String,
    var name : String,
    var category : String,
    var  price : String,
    var quantity : Int,
    var description:String,
    var  imageLink : String,
    var CPUSocketType:String,
    var formFactor:String,
    var memoryFrequencyMax:Int,
    var memoryMax:Int,
    var memoryNumberOfSlots:Int,
    var memoryPinsOfSlots:Int,
    var chipset:String
):Parcelable {
    constructor():this("","","","",0,"","","","",0,0,0,0,"")
}