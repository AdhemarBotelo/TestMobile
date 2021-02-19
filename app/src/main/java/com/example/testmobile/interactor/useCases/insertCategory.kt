package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CategoryRepository
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity

class insertCategory(val categoryRepository: CategoryRepository) :
    UseCase<Boolean, CategoryEntity>() {
    override suspend fun run(params: CategoryEntity): Either<Failure, Boolean> {
        return categoryRepository.insertCategory(params)
    }

}