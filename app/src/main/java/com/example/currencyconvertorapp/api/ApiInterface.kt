package com.example.currencyconvertorapp.api

import com.example.currencyconvertorapp.model.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/latest?apikey=fca_live_ZIKcadn7zq9nJiBUfAwtDGi74f3EsKfurZIy1NrG")
    suspend fun getCurrency(): Response<Currency>
}
