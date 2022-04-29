package com.example.shoppingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class CheckoutViewModel(id: Int, initialQuantity: Int): ViewModel() {

    /*
    * Live data is the used when we want to make a change to a a certain property and want that change to reflect in the whole project without changing it manually.
    * It is lifecycle aware i.e. it holds the data when configuration is changed
    * MutableLiveData is a live data which can be modified just like "var"
    * LiveData is a live data which cannot be modified just like "val"
    */

    private var _qty = MutableLiveData<Int>(initialQuantity)
//    val qty: LiveData<Int>
//        get() = _qty

    val qty: LiveData<Int>
        get() = _qty

    private val _product = MutableLiveData<Product>(products.find { id == it.id })
    val product: LiveData<Product>
        get() = _product

    /*
    * Transformations class lets us manipulate LiveData
    * map function is mapping the particular value
    * first argument is the live data and second is the lambda or the function
    * There is one more function named "switchMap" which is used to pass the whole LiveData object as a whole
    */
    val trimmedDesc: LiveData<String> = Transformations.map(_product, ::trimDesc)

    private fun trimDesc(product: Product): String {
        return product.longDescription.substring(0, 50).uppercase(Locale.getDefault())
    }

    // whenever we want to access the live data object, "value" property is used
    fun addQty() {
        _qty.value?.let {
            _qty.value = it + 1
        }
    }

    fun reduceQty() {
        _qty.value?.let {
            if((it - 1) > 0)
                _qty.value = it - 1
        }
    }
}