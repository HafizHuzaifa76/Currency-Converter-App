package com.example.currencyconvertorapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertorapp.api.ApiInterface
import com.example.currencyconvertorapp.model.Currency
import com.example.currencyconvertorapp.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(private val currencyRepository: CurrencyRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.getCurrency()
        }
    }
    val currData : LiveData<Currency>
        get() = currencyRepository.currencyData


    fun updatedCurrency(newValue:String){
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.getCurrencyValue(newValue)
        }

        Log.d("Change:","${currencyVal.value}")
    }

    val currencyVal: LiveData<Double>
        get() = currencyRepository.currencyValue

}