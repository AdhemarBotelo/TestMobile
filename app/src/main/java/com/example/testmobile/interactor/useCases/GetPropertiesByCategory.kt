package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CarRepository
import com.example.testmobile.interactor.entities.PropertyEntity

class GetPropertiesByCategory(private val carRepository: CarRepository) :
    UseCase<List<PropertyEntity>, String>() {

    override suspend fun run(categoryId: String): Either<Failure, List<PropertyEntity>> {
        return carRepository.getPropertyesByCategory(categoryId)
    }
}