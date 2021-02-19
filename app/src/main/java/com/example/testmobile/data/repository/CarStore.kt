package com.example.testmobile.data.repository

import com.example.testmobile.data.models.*

interface CarStore {
    fun getAllCars(): List<Car>

    fun getCar(id: Long): Pair<Car, List<CarPropertyCrossRef>>

    fun insertCar(car: Car, property: List<CarPropertyCrossRef>)

    fun updateCar(car: Car, property: List<CarPropertyCrossRef>)

    fun deleteCar(id: Long)

    fun getCategories(): List<Category>

    fun getPropertyByCategory(categoryId: String): List<Property>

    fun insertDefaultCategories();
}
