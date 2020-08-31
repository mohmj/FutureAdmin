package com.example.futureadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.futureadmin.Services.Categories
import com.example.futureadmin.Services.Database
import kotlinx.android.synthetic.main.activity_computer_parts.*

class ComputerPartsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_parts)

        var category=Categories()

        computer_parts_activity_desktop_container_layout.setOnClickListener(){
            addProductActivity(category.desktop,"Desktop")
        }
        computer_parts_activity_CPU_container_layout.setOnClickListener(){
            startActivity(Intent(this,AddProductComputerIntelCPUActivity::class.java))
        }
        computer_parts_activity_GPU_container_layout.setOnClickListener(){
            addProductActivity(category.GPU,"GPU")
        }
        computer_parts_activity_motherboard_container_layout.setOnClickListener(){
            startActivity(Intent(this,AddProductComputerMohterboardActivity::class.java))
        }
        computer_parts_activity_RAM_container_layout.setOnClickListener(){
            startActivity(Intent(this,AddProductComputerRamActivity::class.java))
        }
        computer_parts_activity_hardDisk_container_layout.setOnClickListener(){
            addProductActivity(category.memory,"Memory")
        }
        computer_parts_activity_CPU_cooler_container_layout.setOnClickListener(){
            startActivity(Intent(this,AddProductComputerCoolerActivity::class.java))
        }

        computer_parts_activity_cases_container_layout.setOnClickListener(){
            startActivity(Intent(this,AddProductComputerCaseActivity::class.java))
        }
        computer_parts_activity_headphone_container_layout.setOnClickListener(){
            addProductActivity(category.headphone,"Headphone")
        }
        computer_parts_activity_soundCard_container_layout.setOnClickListener(){
            addProductActivity(category.sound_card,"Sound card")
        }
    }

    fun addProductActivity(category:String,categoryTitle: String){
        var intent= Intent(this,AddProductActivity::class.java)
        intent.putExtra(Database().category,"computer/$category")
        intent.putExtra(Database().categoryTitle,categoryTitle)
        startActivity(intent)
    }
}