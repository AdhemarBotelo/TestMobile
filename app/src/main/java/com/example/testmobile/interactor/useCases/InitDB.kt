package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CarRepository

class InitDB constructor(private val carRepository: CarRepository) :
    UseCase<Boolean, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, Boolean> =
        carRepository.createDefaultCategories()
}