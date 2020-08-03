package com.example.futureadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Toast
import com.example.futureadmin.Services.Database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        supportActionBar?.title=intent.getStringExtra(Database().categoryTitle)
        var category=intent.getStringExtra(Database().category).toString()

        add_product_activity_button.setOnClickListener(){
            var productName=add_product_activity_name_edit_text.text.toString()
            var productPrice=add_product_activity_price_edit_text.text.toString()
            var productDescription=add_product_activity_description_edit_text.text.toString()

            if(productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty()){
                var reference= Firebase.database.getReference("products/$category").push()
                reference.setValue(ProductInformation(productName,productPrice,"",productDescription)).addOnSuccessListener {
                    Toast.makeText(this,"The item was added successfully",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }else{
                Toast.makeText(this,"Please fill the data",Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class ProductInformation(var name:String, var price:String, var imageLink:String,var description: String){
    constructor():this("","","","")
}