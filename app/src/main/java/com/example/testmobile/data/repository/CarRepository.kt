package com.example.testmobile.data.repository

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity

interface CarRepository {
    fun getAllCars(): Either<Failure, List<CarEntity>>
    fun getCar(id: Long): Either<Failure, CarEntity>
    fun insertCar(car: CarEntity): Either<Failure, Boolean>
    fun updateCar(car: CarEntity): Either<Failure, Boolean>
    fun deleteCar(id: Long): Either<Failure, Boolean>

    fun getCategories():Either<Failure, List<CategoryEntity>>

    fun createDefaultCategories():Either<Failure, Boolean>
}