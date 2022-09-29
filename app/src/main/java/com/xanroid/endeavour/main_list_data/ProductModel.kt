package com.xanroid.endeavour.main_list_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductModel(

    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String,
    val image: String,
    val price: Double,
    val rating: Double,
    var isFavourite: Boolean = false

)