package com.example.lunchtime.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lunchtime.data.DataSource
import java.text.NumberFormat

class OrderViewModel : ViewModel() {

    // map of menu items
    val menuItems = DataSource.menuItems

    // Default values for item prices
    private var previousEntreePrice = 0.0
    private var previousSidePrice = 0.0
    private var previousAccompanimentPrice = 0.0

    // Default tax rate
    private val taxRate = 0.08

    // Entree for the Order
    private val _entree = MutableLiveData<MenuItem>()
    val entree: LiveData<MenuItem> get() = _entree

    // SideMenu for the Order
    private val _side = MutableLiveData<MenuItem>()
    val side: LiveData<MenuItem> = _side

    // Accompaniment for the order.
    private val _accompaniment = MutableLiveData<MenuItem>()
    val accompaniment: LiveData<MenuItem> = _accompaniment

    // Subtotal for the order
    private val _subtotal = MutableLiveData(0.0)
    var subtotal: LiveData<String> = Transformations.map(_subtotal) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    // Total cost of the order
    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = Transformations.map(_total) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    // Tax for the order
    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = Transformations.map(_tax) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    /**
     * Set the entree for the order.
     */
    fun setEntree(entree: String) {
        // set the current entree value to the menu item corresponding to the passed in string
        _entree.value = menuItems[entree]
        // set the previous entree price to the current entree price.
        previousEntreePrice = menuItems[entree]?.price ?: 0.0
        //  if _subtotal.value is not null subtract the previous entree price from the current
        //  subtotal value. This ensures that we only charge for the currently selected entree.
        if (_subtotal.value != null) previousEntreePrice -= _subtotal.value!!

        // update the subtotal to reflect the price of the selected entree.
        updateSubtotal(previousEntreePrice)
//        calculateTaxAndTotal()
    }

    /**
     * Set the side for the order.
     */
    fun setSide(side: String) {
        // set the current side value to the menu item corresponding to the passed in string
        _side.value = menuItems[side]
        // set the previous side price to the current side price.
        if (_side.value != null) previousSidePrice = menuItems[side]?.price ?: 0.0
        //  if _subtotal.value is not null subtract the previous side price from the current
        //  subtotal value. This ensures that we only charge for the currently selected side.
        if (_subtotal.value != null) previousSidePrice -= _subtotal.value!!

        //  update the subtotal to reflect the price of the selected side.
        updateSubtotal(previousSidePrice)
    }

    /**
     * Set the accompaniment for the order.
     */
    fun setAccompaniment(accompaniment: String) {
        // set the current accompaniment value to the menu item corresponding to the passed in string
        _accompaniment.value = menuItems[accompaniment]
        // set the previous accompaniment price to the current accompaniment price.
        if (_accompaniment.value != null) previousAccompanimentPrice =
            menuItems[accompaniment]?.price ?: 0.0
        //  if _accompaniment.value is not null subtract the previous accompaniment price from
        //  the current subtotal value.
        if (_subtotal.value != null) previousAccompanimentPrice -= _subtotal.value!!

        // update the subtotal to reflect the price of the selected accompaniment.
        updateSubtotal(previousAccompanimentPrice)
        calculateTaxAndTotal()
    }

    /**
     * Update subtotal value.
     */
    private fun updateSubtotal(itemPrice: Double) {
        //  if _subtotal.value is not null, update it to reflect the price of the recently added item.
        //  Otherwise, set _subtotal.value to equal the price of the item.
        if (_subtotal.value != null) _subtotal.value = itemPrice + _subtotal.value!!
            else _subtotal.value = itemPrice
    }

    fun summarySubtotal(): Double {
        val entreePrice = _entree.value?.price
        val sidePrice = _side.value?.price
        val accompanimentPrice = _accompaniment.value?.price
        return entreePrice!! + sidePrice!! + accompanimentPrice!!
    }

    /**
     * Calculate tax and update total.
     */
    private fun calculateTaxAndTotal() {
        //  set _tax.value based on the subtotal and the tax rate.
        _tax.value = taxRate * summarySubtotal()
        //  set the total based on the subtotal and _tax.value.
        _total.value = summarySubtotal() + _tax.value!!
    }

    /**
     * Reset all values pertaining to the order
     */
    fun resetOrder() {
        _subtotal.value = 0.0
        _total.value = 0.0
    }
}