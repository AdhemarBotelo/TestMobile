package com.example.testmobile.data.repository

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.interactor.entities.CategoryEntity

interface CategoryRepository {
    fun getCategories(): Either<Failure, List<CategoryEntity>>
    fun insertCategory(category: CategoryEntity): Either<Failure, Boolean>
    fun deleteCategory(idCategory: String): Either<Failure, Boolean>
}