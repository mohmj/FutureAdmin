package com.example.futureadmin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.futureadmin.Models.ProductInformation
import com.example.futureadmin.Services.Database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        supportActionBar?.title = intent.getStringExtra(Database().categoryTitle)
        var category = intent.getStringExtra(Database().category).toString()

        add_product_activity_button.setOnClickListener() {

            val progressDialog= ProgressDialog(this)
            progressDialog.setTitle("Wait")
            progressDialog.setMessage("Please wait product is adding now")
            progressDialog.show()

            var productName = add_product_activity_name_edit_text.text.toString()
            var productPrice = add_product_activity_price_edit_text.text.toString()
            var productDescription = add_product_activity_description_edit_text.text.toString()
            var storageReference = Firebase.storage.getReference("$category/$productName")
            if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty()) {
                storageReference.putFile(selectImageUri!!).addOnSuccessListener {
                        storageReference.downloadUrl.addOnSuccessListener {
                            var productImageLink=it.toString()
                            var reference = Firebase.database.getReference("products/$category").push()
                            reference.setValue(
                                ProductInformation(
                                    productName,
                                    productPrice,
                                    productImageLink,
                                    productDescription
                                )
                            ).addOnSuccessListener {
                                progressDialog.hide()
                                Toast.makeText(this, "The item was added successfully", Toast.LENGTH_SHORT)
                                    .show()
                                var intent=Intent(this,MainActivity::class.java)
                                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)

                            }.addOnFailureListener() {
                                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Please fill data and choose image.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
            //----------------------------------------------------------------------------------------------------------------------
            // Select photo
            add_product_activity_select_image_button.setOnClickListener() {
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
                add_product_activity_select_image_button.text = ""
                add_product_activity_select_image_button.setBackgroundDrawable(bitmapDrawable)

            }
        }
        //----------------------------------------------------------------------------------------------------------------------
    }




