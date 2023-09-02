package com.example.currencyconvertorapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyconvertorapp.api.ApiInterface
import com.example.currencyconvertorapp.model.Currency
import com.example.currencyconvertorapp.room.CurrencyDatabase
import com.example.currencyconvertorapp.util.MyUtil

class CurrencyRepository(private val apiInterface: ApiInterface, private val currencyDatabase: CurrencyDatabase, private val context: Context) {

    private val currencyLiveData = MutableLiveData<Currency>()
    private val currencyValueLiveData = MutableLiveData<Double>()

    val currencyData: LiveData<Currency>
        get() = currencyLiveData

    val currencyValue: LiveData<Double>
        get() = currencyValueLiveData

    suspend fun getCurrency() {
            val offlineData = currencyDatabase.currencyDataDao().getData()
            val offlineCurrency = Currency(offlineData)
            currencyLiveData.postValue(offlineCurrency)
//        }
    }
    suspend fun getCurrencyValue(name:String){
        val currentData = currencyDatabase.currencyDataDao().getData()
        val value:Double = when(name){
            "USD" -> currentData.USD.toDouble()
            "AUD" -> currentData.AUD
            "BGN" -> currentData.BGN
            "BRL" -> currentData.BRL
            "CAD" -> currentData.CAD
            "CHF" -> currentData.CHF
            "CNY" -> currentData.CNY
            "CZK" -> currentData.CZK
            "DKK" -> currentData.DKK
            "EUR" -> currentData.EUR
            "GBP" -> currentData.GBP
            "HKD" -> currentData.HKD
            "HRK" -> currentData.HRK
            "HUF" -> currentData.HUF
            "IDR" -> currentData.IDR
            "ILS" -> currentData.ILS
            "INR" -> currentData.INR
            "ISK" -> currentData.ISK
            "JPY" -> currentData.JPY
            "KRW" -> currentData.KRW
            "MXN" -> currentData.MXN
            "MYR" -> currentData.MYR
            "NOK" -> currentData.NOK
            "NZD" -> currentData.NZD
            "PHP" -> currentData.PHP
            "PLN" -> currentData.PLN
            "RON" -> currentData.RON
            "RUB" -> currentData.RUB
            "SEK" -> currentData.SEK
            "SGD" -> currentData.SGD
            "THB" -> currentData.THB
            "TRY" -> currentData.TRY
            "ZAR" -> currentData.ZAR
            else -> 0.0
        }
        Log.d("RepoGetCurrency","$value , $name")
        currencyValueLiveData.postValue(value)
    }
}