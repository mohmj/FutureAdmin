package com.example.futureadmin.Models.computer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

class ComputerCase (
    var productNumbrer:String,
    var name : String,
    var category : String,
    var  price : Double,
    var quantity : Int,
    var  imageLink : String,
    var description : String,
    var type:String,
    var formFactor:String
    ):
    Parcelable {
    constructor():this("","","",0.0,0,"","","","")
}