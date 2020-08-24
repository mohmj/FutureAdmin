package com.example.futureadmin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.futureadmin.GetPcParts.GetMotherboardPinsOfSlots
import com.example.futureadmin.GetPcParts.GetRAMType
import com.example.futureadmin.Models.computer.ComputerRAM
import com.example.futureadmin.Services.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product_computer_mohterboard.*
import kotlinx.android.synthetic.main.activity_add_product_computer_ram.*

class AddProductComputerRamActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;

    var RAMType: String = "";
    var RAMTypeList = mutableListOf<String>();

    var pins: Int = 0
    var pinsList = mutableListOf<Int>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_computer_ram)



        Database().RAM_type_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var type=it.getValue(GetRAMType::class.java)
                    if(type != null){
                        RAMTypeList.add(type.RAM_type)
                    }
                }
            }

        })

        Database().RAM_pins_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var pin=it.getValue(GetMotherboardPinsOfSlots::class.java)
                    if(pin != null){
                        pinsList.add(pin.pins)
                    }
                }
            }

        })




        add_product_computer_RAM_activity_button.setOnClickListener(){
            var name=add_product_RAM_activity_name_edit_text.text.toString()
            var category="computer/RAM"
            var price=add_product_RAM_activity_price_edit_text.text.toString()
            var quantity=add_product_RAM_activity_quantity_edit_text.text.toString().toInt()
            var description=add_product_RAM_activity_description_edit_text.text.toString()
            var speed=add_product_RAM_activity_speed_edit_text.text.toString().toInt()
            var numberOfSlice=add_product_RAM_activity_number_of_slice_edit_text.text.toString().toInt()
            var singleGiga=add_product_RAM_activity_giga_for_single_bar_edit_text.text.toString().toInt()
            var totalGiga=numberOfSlice*singleGiga
            var CASLatency=add_product_RAM_activity_CAS_latency_edit_text.text.toString().toInt()

            var productNumber= Firebase.database.getReference("computer/RAM").push().key.toString()
            var storageReference=Firebase.storage.getReference("computer/RAM/$productNumber")
            var databaseReference=Firebase.database.getReference("products/computer/RAM/$productNumber")

            storageReference.putFile(selectImageUri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageLink=it.toString()
                    databaseReference.setValue(ComputerRAM(
                        productNumber,
                        name,
                        category,
                        price,
                        quantity,
                        imageLink,
                        description,
                        RAMType,
                        pins,
                        speed,
                        numberOfSlice,
                        singleGiga,
                        totalGiga,
                        CASLatency
                    )).addOnSuccessListener {
                        finish()
                    }
                }
            }

        }


        //----------------------------------------------------------------------------------------------------------------------
        // Select photo
        add_product_computer_RAM_activity_select_image_button.setOnClickListener() {
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
            add_product_computer_RAM_activity_select_image_button.text = ""
            add_product_computer_RAM_activity_select_image_button.setBackgroundDrawable(
                bitmapDrawable
            )

        }
    }


    //----------------------------------------------------------------------------------------------------------------------


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.get_spinner_item,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        when(id){
            R.id.get_spinner_item_menu_button->{
                var arrayListForRamType=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,RAMTypeList)
                add_product_RAM_activity_type_of_RAM_spinner.adapter=arrayListForRamType
                add_product_RAM_activity_type_of_RAM_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        RAMType=RAMTypeList[p2]
                    }

                }

                var arrayListForPins=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,pinsList)
                add_product_RAM_activity_pins_spinner.adapter=arrayListForPins
                add_product_RAM_activity_pins_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        pins=pinsList[p2]
                    }

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

