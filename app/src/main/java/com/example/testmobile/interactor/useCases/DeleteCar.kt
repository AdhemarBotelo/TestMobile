package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CarRepository

class DeleteCar(private val carRepository: CarRepository) : UseCase<Boolean, Long>() {
    override suspend fun run(carId: Long): Either<Failure, Boolean> {
        return carRepository.deleteCar(carId)
    }
}