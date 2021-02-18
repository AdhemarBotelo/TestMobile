package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CarRepository
import com.example.testmobile.interactor.entities.CarEntity

class GetCars
constructor(private val carRepository: CarRepository) : UseCase<List<CarEntity>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<CarEntity>> =
        carRepository.getAllCars()
}