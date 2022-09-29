package com.xanroid.endeavour.main_list_data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(private val dao: ProductDao) {

    fun readAllData(): Flow<List<ProductModel>> {
        return dao.getAllProduct().flowOn(Dispatchers.IO)
    }
    suspend fun addProduct(product: ProductModel){
        dao.insert(product)
    }
    suspend fun deleteProduct(product: ProductModel){
        dao.delete(product)
    }
}