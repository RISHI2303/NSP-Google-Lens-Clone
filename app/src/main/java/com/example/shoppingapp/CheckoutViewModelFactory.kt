package com.example.shoppingapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
* This is a BoilerPlate Code.
* We just have to change the parameters.
* The parameters should be of type that we passed in view model class. In this case, id and quantity
* And in return statement, pass those parameters.
* Rest whole code will be same and we just have to change the class name
*/

class CheckoutViewModelFactory(private val id: Int, private val quantity: Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CheckoutViewModel(id, quantity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
