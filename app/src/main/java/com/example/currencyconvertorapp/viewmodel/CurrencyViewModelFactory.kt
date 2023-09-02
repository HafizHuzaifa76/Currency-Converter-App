package com.example.currencyconvertorapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconvertorapp.repository.CurrencyRepository

class CurrencyViewModelFactory(private val currencyRepository: CurrencyRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(currencyRepository) as T
    }
}