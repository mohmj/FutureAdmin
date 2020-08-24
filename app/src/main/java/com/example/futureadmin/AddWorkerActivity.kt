package com.example.futureadmin

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.futureadmin.Models.WorkerInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_worker.*

class AddWorkerActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_worker)



        add_worker_activity_button.setOnClickListener(){
            var name=add_worker_activity_name_edit_text.text.toString()
            var email=add_worker_activity_email_edit_text.text.toString()
            var phoneNumber=add_worker_activity_phone_number_edit_text.text.toString()
            var password=add_worker_activity_password_edit_text.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                var uid=Firebase.auth.uid
                var storeageReference= Firebase.storage.getReference("workers/$uid")
               storeageReference.putFile(selectImageUri!!).addOnSuccessListener {
                    storeageReference.downloadUrl.addOnSuccessListener {
                        var imageLink=it.toString()
                        Firebase.database.getReference("workers/$uid").setValue(WorkerInformation(uid.toString(),name,email,phoneNumber,imageLink)).addOnSuccessListener {
                            finish()
                        }
                    }
                }
            }

        }


//----------------------------------------------------------------------------------------------------------------------
        // Select photo
        add_worker_activity_select_image_button.setOnClickListener() {
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
            add_worker_activity_select_image_button.text = ""
            add_worker_activity_select_image_button.setBackgroundDrawable(bitmapDrawable)

        }
    }
//----------------------------------------------------------------------------------------------------------------------
}