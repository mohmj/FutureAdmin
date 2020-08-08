package com.example.futureadmin.Models

import android.os.Parcelable
import android.widget.Toast
import com.google.firebase.database.ktx.database
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductInformation(var name:String,var category:String ,var price:String,var quantity:Int, var imageLink:String,var description: String):
    Parcelable {
    constructor():this("","","",1,"","")
}

//

//    }
