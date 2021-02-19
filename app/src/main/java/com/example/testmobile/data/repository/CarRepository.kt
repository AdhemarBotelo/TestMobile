package com.example.testmobile.data.repository

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.entities.PropertyEntity

interface CarRepository {
    fun getAllCars(): Either<Failure, List<CarEntity>>
    fun getCar(id: Long): Either<Failure, CarEntity>
    fun insertCar(car: CarEntity): Either<Failure, Boolean>
    fun updateCar(car: CarEntity): Either<Failure, Boolean>
    fun deleteCar(id: Long): Either<Failure, Boolean>

    fun getCategories(): Either<Failure, List<CategoryEntity>>

    fun getPropertyesByCategory(categoryId: String): Either<Failure, List<PropertyEntity>>
    fun createDefaultCategories(): Either<Failure, Boolean>
}