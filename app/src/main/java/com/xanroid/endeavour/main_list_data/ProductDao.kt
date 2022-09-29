package com.xanroid.endeavour.main_list_data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getAllProduct(): Flow<List<ProductModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductModel)

    @Delete
    suspend fun delete(product: ProductModel)

//    @Query("DELETE FROM product_table")
//    suspend fun removeAll()
//
//    @Update
//    suspend fun updateProduct(product: ProductModel)

}