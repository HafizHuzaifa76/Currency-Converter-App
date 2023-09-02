package com.example.currencyconvertorapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {
    private val BASE_URL = "https://api.freecurrencyapi.com/v1/"

    fun getInstance(): Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
}