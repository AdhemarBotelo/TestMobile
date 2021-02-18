package com.example.testmobile.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testmobile.data.models.*

@Database(entities = [Car::class, Category::class, Property::class,CarPropertyCrossRef::class], version = 1)
abstract class CarsDatabase : RoomDatabase() {
    abstract fun getCarDao(): CarDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getPropertyDao(): PropertyDao
}