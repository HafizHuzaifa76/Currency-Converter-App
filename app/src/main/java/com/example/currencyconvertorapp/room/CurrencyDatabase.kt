package com.example.currencyconvertorapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconvertorapp.model.Data

@Database(entities = [Data::class], version = 1)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDataDao():CurrencyDataDao

    companion object{
        private final val DATABASE_NAME = "CurrencyConverterDatabase"

        private var INSTANCE:CurrencyDatabase? = null

        fun getInstance(context: Context):CurrencyDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,CurrencyDatabase::class.java, DATABASE_NAME).build()
            }
            return INSTANCE!!
        }
    }
}