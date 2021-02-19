package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CarRepository
import com.example.testmobile.interactor.entities.CarEntity

class UpdateCar(private val carRepository: CarRepository) : UseCase<Boolean, CarEntity>() {
    override suspend fun run(car: CarEntity): Either<Failure, Boolean> {
        return carRepository.updateCar(car)
    }
}