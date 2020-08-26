package com.example.futureadmin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.futureadmin.GetPcParts.GetCaseType
import com.example.futureadmin.GetPcParts.GetMotherboardFormFactor
import com.example.futureadmin.Models.computer.ComputerCase
import com.example.futureadmin.Services.Database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_product_computer_case.*
import kotlinx.android.synthetic.main.activity_add_product_computer_intel_c_p_u.*

class AddProductComputerCaseActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;

    var type:String=""
    var typeList= mutableListOf<String>()

    var formFactor:String=""
    var formFactorList= mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_computer_case)

        Database().motherboard_form_factor_reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var form=it.getValue(GetMotherboardFormFactor::class.java)
                    if(form != null){
                        formFactorList.add(form.form_factor)
                    }
                }
            }

        })

        Database().case_type.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var theType=it.getValue(GetCaseType::class.java)
                    if(theType != null){
                        typeList.add(theType.type)
                    }
                }
            }

        })


        add_product_computer_case_activity_button.setOnClickListener(){
            var name=add_product_computer_case_activity_name_edit_text.text.toString()
            var category="computer/case"
            var productNumber= Firebase.database.getReference("products/$category").push().key.toString()
            var price=add_product_computer_case_activity_price_edit_text.text.toString()
            var quantity=add_product_computer_case_quantity_activity_edit_text.text.toString().toInt()
            var description=add_product_computer_case_activity_description_edit_text.text.toString()

            var storageReference=Firebase.storage.getReference("computer/case/$productNumber")
            var databaseReference=Firebase.database.getReference("products/computer/case/$productNumber")

            storageReference.putFile(selectImageUri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    var imageLink=it.toString()
                    databaseReference.setValue(ComputerCase(
                        productNumber,
                        name,
                        category,
                        price.toDouble(),
                        quantity,
                        imageLink,
                        description,
                        type,
                        formFactor
                    )).addOnSuccessListener {
                        finish()
                    }
                }
            }
        }


        //----------------------------------------------------------------------------------------------------------------------
        // Select photo
        add_product_computer_case_activity_select_image_button.setOnClickListener() {
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
            add_product_computer_case_activity_select_image_button.text = ""
            add_product_computer_case_activity_select_image_button.setBackgroundDrawable(
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
                var arrayAdapterOfFormFactor=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,formFactorList)
                add_product_computer_case_activity_form_factor_spinner.adapter=arrayAdapterOfFormFactor
                add_product_computer_case_activity_form_factor_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        formFactor=formFactorList[p2]
                    }

                }

                var arrayAdapterOfCaseType=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,typeList)
                add_product_computer_case_activity_type_spinner.adapter=arrayAdapterOfCaseType
                add_product_computer_case_activity_type_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        type=typeList[p2]
                    }

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}