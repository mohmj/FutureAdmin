package com.example.futureadmin.Services

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Database {
    var category="category"
    var categoryTitle="category_title"
    var builder="builder"
    var builderReference=Firebase.database.getReference("$builder")
    var CPU_socket_types_reference=Firebase.database.getReference("$builder/CPU_socket_types")
    var motherboard_form_factor_reference=Firebase.database.getReference("$builder/motherboard_form_factor")
    var motherboard_memory_max_reference=Firebase.database.getReference("$builder/motherboard_memory_max")
    var motherboard_memory_pins_reference=Firebase.database.getReference("$builder/ram_pins")
    var motherboard_chipset_reference=Firebase.database.getReference("$builder/chipset")
    var RAM_type_reference=Firebase.database.getReference("$builder/ram_type")
    var RAM_pins_reference=Firebase.database.getReference("$builder/ram_pins")

}