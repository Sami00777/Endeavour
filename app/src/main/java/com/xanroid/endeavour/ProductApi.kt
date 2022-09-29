package com.xanroid.endeavour

import com.xanroid.endeavour.main_model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {

    @GET("2f06b453-8375-43cf-861a-06e95a951328")
    fun getData(): Call<Product>

}