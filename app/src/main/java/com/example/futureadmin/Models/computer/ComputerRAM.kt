package com.example.futureadmin.Models.computer

class ComputerRAM (
    var productNumbrer:String,
    var name : String,
    var category : String,
    var  price : String,
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
) {
    constructor():this( "","","","",0,"",
                        "","",0,0,0,
                        0,0,0)
}