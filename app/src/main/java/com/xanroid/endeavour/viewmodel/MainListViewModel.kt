package com.xanroid.endeavour.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xanroid.endeavour.BASE_URL
import com.xanroid.endeavour.ProductApi
import com.xanroid.endeavour.main_list_data.ProductModel
import com.xanroid.endeavour.main_model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainListViewModel: ViewModel() {

    val products = MutableLiveData<List<ProductModel>>()
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading get() = _isLoading

    init {
        getDataFromApi()
    }


    private fun getDataFromApi() {

        val fetchData = mutableListOf<ProductModel>()
        _isLoading.value = true

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ProductApi::class.java)
        val retrofitData = retrofit.getData()
        retrofitData.enqueue(object : Callback<Product?> {
            override fun onResponse(call: Call<Product?>, response: Response<Product?>) {
                if (response.isSuccessful){
                    Log.i("retrofit", "successful response")
                    val responseBody = response.body()

                    if (responseBody != null){
                        Log.i("retrofit", "Response not null")

                        for (item in responseBody.products){
                            val id = item.id.toInt()
                            val name = item.title
                            val image = item.imageURL
                            val price = item.price
                            val rating = item.ratingCount
                            val tempProduct = ProductModel(
                                id = id,
                                name = name,
                                image = image,
                                price = price[0].value,
                                rating = rating,
                                isFavourite = false
                            )
                            fetchData.add(tempProduct)
                        }
                        products.value = fetchData
                        _isLoading.value = false
                    } else {
                        Log.i("retrofit", "Response is null")
                        _isLoading.value = false
                    }
                } else {
                    Log.i("retrofit", "response was not successful")
                    _isLoading.value = false
                }
            }
            override fun onFailure(call: Call<Product?>, t: Throwable) {
                Log.i("retrofit", "onFailure Error: ${t.message}")
                _isLoading.value = false
            }
        })
    }




}