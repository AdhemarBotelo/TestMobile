package com.example.testmobile.data.repository

import com.example.testmobile.core.Either
import com.example.testmobile.core.Failure
import com.example.testmobile.data.models.Category
import com.example.testmobile.interactor.entities.CategoryEntity

class CategoryRepositoryImp(private val carStoreImpl: CarStore) : CategoryRepository {
    override fun getCategories(): Either<Failure, List<CategoryEntity>> {
        return try {
            var result = carStoreImpl.getCategories().map { categoryToEntity(it) }
            Either.Right(result)
        } catch (exception: Throwable) {
            Either.Left(Failure.DataBaseError)
        }
    }

    override fun insertCategory(category: CategoryEntity): Either<Failure, Boolean> {
        return try {
            carStoreImpl.insertCategory(Category(category.categoryId))
            Either.Right(true)
        } catch (exception: Throwable) {
            Either.Left(Failure.DataBaseError)
        }
    }

    override fun deleteCategory(idCategory: String): Either<Failure, Boolean> {
        return try {
            carStoreImpl.deleteCategory(idCategory)
            Either.Right(true)
        } catch (exception: Throwable) {
            Either.Left(Failure.DataBaseError)
        }
    }

    private fun categoryToEntity(category: Category): CategoryEntity {
        return CategoryEntity(category.categoryId)
    }
}