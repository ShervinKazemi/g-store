package com.example.gabistore.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gabistore.model.data.Product

@Database(entities = [Product::class] , version = 1 , exportSchema = false)
abstract class AppDataBase :RoomDatabase() {
    abstract fun productDao() :ProductDao
}