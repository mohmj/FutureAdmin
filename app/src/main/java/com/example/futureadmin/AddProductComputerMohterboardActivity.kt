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
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.futureadmin.GetPcParts.*
import com.example.futureadmin.Models.computer.ComputerMotherboard
import com.example.futureadmin.Services.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_product_computer_intel_c_p_u.*
import kotlinx.android.synthetic.main.activity_add_product_computer_mohterboard.*

class AddProductComputerMohterboardActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;

    var CPUSocketType=""
    var CPUSocketList= mutableListOf<String>();

    var formFactor=""
    var formFactorList= mutableListOf<String>()

    var memoryMax=0
    var memoryMaxList= mutableListOf<Int>()

    var memoryNumberOfSlots=0
    var memoryNumberOfSlotsList= arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)

    var memoryPinsOfSlots=0
    var memoryPinsOfSlotsList= mutableListOf<Int>()

    var chipset=""
    var chipsetList= mutableListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_computer_mohterboard)

        //Get cpu sockets
        Database().CPU_socket_types_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var socket=it.getValue(GetCPUSocket::class.java)
                    if(socket != null){
                        CPUSocketList.add(socket.socket_type)
                    }
                }
            }

        })

        //Get motherboard form factor
        Database().motherboard_form_factor_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var formFactor=it.getValue(GetMotherboardFormFactor::class.java)
                    if(formFactor != null){
                        formFactorList.add(formFactor.form_factor)
                    }
                }
            }

        })

        //Get motherboard memory max
        Database().motherboard_memory_max_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var memory_max=it.getValue(GetMotherboardMemoryMax::class.java)
                    if(memory_max != null){
                        memoryMaxList.add(memory_max.memory_max)
                    }
                }
            }

        })

        //Get motherboard memory pins
        Database().motherboard_memory_pins_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var pin=it.getValue(GetMotherboardPinsOfSlots::class.java)
                    if(pin != null){
                        memoryPinsOfSlotsList.add(pin.pins)
                    }
                }
            }

        })

        Database().motherboard_chipset_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var theDhipset=it.getValue(GetChipset::class.java)
                    if(theDhipset != null){
                        chipsetList.add(theDhipset.chipset)
                    }
                }
            }

        })


        add_product_computer_motherboard_activity_button.setOnClickListener(){
            var name=add_product_motherboard_activity_name_edit_text.text.toString()
            var category="computer/motherboard"
            var price=add_product_motherboard_activity_price_edit_text.text.toString()
            var quantity=add_product_motherboard_activity_quantity_edit_text.text.toString().toInt()
            var maxMemoryFrequency=add_product_motherboard_activity_max_memory_requency_support_edit_text.text.toString().toInt()
            var description=add_product_motherboard_activity_description_edit_text.text.toString()
            var productNumber= Firebase.database.getReference("products/computer/motherboard").push().key.toString()
            var databaseReference=Firebase.database.getReference("products/computer/motherboard/$productNumber")
            var storageReference=Firebase.storage.getReference("products/computer/motherboard/$productNumber")

            storageReference.putFile(selectImageUri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageLink=it.toString();
                    databaseReference.setValue(
                        ComputerMotherboard(
                            productNumber,
                            name,
                            category,
                            price,
                            quantity,
                            description,
                            imageLink,
                            CPUSocketType,
                            formFactor,
                            maxMemoryFrequency,
                            memoryMax,
                            memoryNumberOfSlots,
                            memoryPinsOfSlots,
                            chipset
                        )
                    ).addOnSuccessListener {
                        finish()
                    }
                }
            }
        }



        //----------------------------------------------------------------------------------------------------------------------
        // Select photo
        add_product_computer_motherboard_activity_select_image_button.setOnClickListener(){
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
            add_product_computer_motherboard_activity_select_image_button.text = ""
            add_product_computer_motherboard_activity_select_image_button.setBackgroundDrawable(bitmapDrawable)

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

                //Socket type
                var arrayListForSocketType=
                    ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,CPUSocketList)
                add_product_motherboard_activity_cpu_socket_type_spinner.adapter=arrayListForSocketType
                add_product_motherboard_activity_cpu_socket_type_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        CPUSocketType=CPUSocketList[p2]
                        Log.d("IBT",CPUSocketType)
                    }
                }


                //Form factor
                var arrayListForMotherboardFormFactor=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,formFactorList)
                add_product_motherboard_activity_form_factor_spinner.adapter=arrayListForMotherboardFormFactor
                add_product_motherboard_activity_form_factor_spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        formFactor=formFactorList[p2]
                        Log.d("toda",formFactor)
                    }

                }

                //Memory max
                var arrayAdapterForMemoryMax=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,memoryMaxList);
                add_product_motherboard_activity_memory_max_spinner.adapter=arrayAdapterForMemoryMax
                add_product_motherboard_activity_memory_max_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        memoryMax=memoryMaxList[p2]
                    }

                }

                //Memory number of slots
                var arrayAdapterMemoryNumberOfSlots=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,memoryNumberOfSlotsList)
                add_product_motherboard_activity_memory_number_of_slots_spinner.adapter=arrayAdapterMemoryNumberOfSlots
                add_product_motherboard_activity_memory_number_of_slots_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        memoryNumberOfSlots=memoryNumberOfSlotsList[p2]
                    }

                }

                //Memory Pins of slots
                var arrayAdapterPinsOfSlots=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,memoryPinsOfSlotsList)
                add_product_motherboard_activity_memory_pins_of_slots_spinner.adapter=arrayAdapterPinsOfSlots
                add_product_motherboard_activity_memory_pins_of_slots_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        memoryPinsOfSlots=memoryPinsOfSlotsList[p2]
                    }

                }

                var arrayAdapterOfChipset=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,chipsetList)
                add_product_motherboard_activity_chipset_spinner.adapter=arrayAdapterOfChipset
                add_product_motherboard_activity_chipset_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        chipset=chipsetList[p2]
                    }

                }


            }
        }
        return super.onOptionsItemSelected(item)
    }
}