package com.example.currencyconvertorapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconvertorapp.model.Data

@Dao
interface CurrencyDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: Data)

    @Query("SELECT * FROM CurrencyData")
    suspend fun getData():Data

    @Query("SELECT IDR FROM CurrencyData WHERE USD = 1")
    suspend fun getCurrencyValue():Double

}