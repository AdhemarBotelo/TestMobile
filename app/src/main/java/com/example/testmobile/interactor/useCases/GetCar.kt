package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CarRepository
import com.example.testmobile.interactor.entities.CarEntity

class GetCar constructor(private val carRepository: CarRepository) : UseCase<CarEntity, Long>() {
    override suspend fun run(carId: Long): Either<Failure, CarEntity> =
        carRepository.getCar(carId)
}