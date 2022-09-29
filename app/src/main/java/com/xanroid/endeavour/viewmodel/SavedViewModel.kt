package com.xanroid.endeavour.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xanroid.endeavour.main_list_data.ProductModel
import com.xanroid.endeavour.main_list_data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repository: ProductRepository): ViewModel() {

    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products get() = _products.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readAllData().collect {
                _products.value = it
            }
        }
    }

    fun addProduct(product: ProductModel){
        viewModelScope.launch {
            repository.addProduct(product)
        }
    }
    fun deleteProduct(product: ProductModel){
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }
}