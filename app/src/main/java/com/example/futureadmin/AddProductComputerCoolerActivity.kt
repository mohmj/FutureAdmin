package com.example.futureadmin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.futureadmin.Models.computer.ComputerCooler
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_product_computer_cooler.*
import kotlinx.android.synthetic.main.activity_add_product_computer_intel_c_p_u.*

class AddProductComputerCoolerActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;
    var cooler_type = ""
    var cooler_list = arrayListOf<String>("air_cooler","water_cooler")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_computer_cooler)



        var arrayListForCoolerType =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cooler_list)
        app_product_computer_cooler_activity_type_spinner.adapter = arrayListForCoolerType
        app_product_computer_cooler_activity_type_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    cooler_type = cooler_list[p2]
                }
            }


        app_product_computer_cooler_activity_button.setOnClickListener(){
            var productNumber=Firebase.database.getReference("products/computer/cooler").push().key.toString()
            var name=app_product_computer_cooler_activity_name_edit_text.text.toString()
            var price=app_product_computer_cooler_activity_price_edit_text.text.toString().toDouble()
            var quantity=app_product_computer_cooler_activity_quantity_edit_text.text.toString().toInt()
            var description=app_product_computer_cooler_activity_description_edit_text.text.toString()

            var category="computer/cooler"
            var storageReference=Firebase.storage.getReference("products/computer/cooler/$productNumber")
            var databaseReference=Firebase.database.getReference("products/computer/cooler/$productNumber")
            storageReference.putFile(selectImageUri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageLink=it.toString()
                    databaseReference.setValue(ComputerCooler(
                        productNumber,
                        name,
                        category,
                        price,
                        quantity,
                        imageLink,
                        description ,
                        cooler_type
                    )).addOnSuccessListener {
                        finish()
                    }
                }
            }
        }


        //----------------------------------------------------------------------------------------------------------------------
        // Select photo
        add_product_computer_cooler_activity_select_image_button.setOnClickListener() {
            var photoIntent = Intent(Intent.ACTION_PICK);
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectImageUri = data.data
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectImageUri);
            var bitmapDrawable = BitmapDrawable(bitmap)
            add_product_computer_cooler_activity_select_image_button.text = ""
            add_product_computer_cooler_activity_select_image_button.setBackgroundDrawable(
                bitmapDrawable
            )

        }
    }
}
        //----------------------------------------------------------------------------------------------------------------------