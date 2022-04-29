package com.example.shoppingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel(id: Int, initialRating: String): ViewModel() {
     val rating = MutableLiveData<String>(initialRating)

    private val _product = MutableLiveData<Product>(products.find { id == it.id })
    val product: LiveData<Product>
        get() = _product

}