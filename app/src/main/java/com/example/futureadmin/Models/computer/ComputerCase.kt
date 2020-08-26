package com.example.futureadmin.Models.computer

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
    ) {
    constructor():this("","","",0.0,0,"","","","")
}