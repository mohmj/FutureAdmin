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
import com.example.futureadmin.GetPcParts.GetCPUSocket
import com.example.futureadmin.Models.computer.ComputerCPU
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product_computer_intel_c_p_u.*

class AddProductComputerIntelCPUActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;
    var CPUSocketType=""
    var CPUSocketList= mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_computer_intel_c_p_u)



        Firebase.database.getReference("builder/CPU_socket_types").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var cpu=it.getValue(GetCPUSocket::class.java)
                    if(cpu != null){
                        CPUSocketList.add(cpu.socket_type.toString());
                    }
                }
            }
        })







        app_product_computer_CPU_activity_button.setOnClickListener(){
            var name=app_product_computer_CPU_activity_name_edit_text.text.toString()
            var category="computer/CPU"
            var price=app_product_computer_CPU_activity_price_edit_text.text.toString()
            var quantity=app_product_computer_CPU_activity_quantity_edit_text.text.toString().toInt()
            var numberOfCores=app_product_computer_CPU_activity_number_of_cores_edit_text.text.toString().toInt()
            var coreClock=app_product_computer_CPU_activity_core_clock_edit_text.text.toString().toDouble()
            var boostClock=app_product_computer_CPU_activity_boost_clock_edit_text.text.toString().toDouble()
            var TDP=app_product_computer_CPU_activity_TDP_edit_text.text.toString().toInt()
            var integratedGraphicsCard=app_product_computer_CPU_activity_integrated_graphics_card_edit_text.text.toString()
            var description=app_product_computer_CPU_activity_description_edit_text.text.toString()

            var productNumber=Firebase.database.getReference("products/computer/CPU").push().key.toString()
            var databaseReference=Firebase.database.getReference("products/computer/CPU/$productNumber")
            var storageReference=Firebase.storage.getReference("computer/CPU/$productNumber")
            storageReference.putFile(selectImageUri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageLink=it.toString()
                    databaseReference.setValue(ComputerCPU(productNumber,
                    name,
                        category,
                        price,
                        quantity,
                        imageLink,
                        description,
                        CPUSocketType,
                        numberOfCores,
                        coreClock,
                        boostClock,
                        TDP,
                        integratedGraphicsCard
                        )).addOnSuccessListener {
                        finish()
                    }
                }
            }

        }


        //----------------------------------------------------------------------------------------------------------------------
        // Select photo
        add_product_computer_intel_CPU_activity_select_image_button.setOnClickListener(){
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
            add_product_computer_intel_CPU_activity_select_image_button.text = ""
            add_product_computer_intel_CPU_activity_select_image_button.setBackgroundDrawable(bitmapDrawable)

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
            var arrayListForSocketType=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,CPUSocketList)
            app_product_computer_CPU_activity_CPU_socket_type_spinner.adapter=arrayListForSocketType
            app_product_computer_CPU_activity_CPU_socket_type_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    CPUSocketType=CPUSocketList[p2]
                    Log.d("mjmjmj",CPUSocketType)
                }
            }
        }
        }
        return super.onOptionsItemSelected(item)
    }

}

