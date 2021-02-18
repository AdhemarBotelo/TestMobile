package com.example.testmobile.data.repository

import com.example.testmobile.data.models.*

interface CarStore {
    fun getAllCars(): List<Car>

    fun getCar(id: Long): CarWithProperties

    fun insertCar(car: Car, property: CarPropertyCrossRef)

    fun updateCar(car: Car, property: Property)

    fun deleteCar(id: Long)

    fun getCategories(): List<Category>

    fun insertDefaultCategories();
}
