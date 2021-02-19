package com.example.testmobile.interactor.useCases

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.repository.CategoryRepository

class deleteCategory(val categoryRepository: CategoryRepository) : UseCase<Boolean, String>() {
    override suspend fun run(categoryID: String): Either<Failure, Boolean> {
        return categoryRepository.deleteCategory(categoryID)
    }
}