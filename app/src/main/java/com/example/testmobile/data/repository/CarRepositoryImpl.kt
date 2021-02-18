package com.example.testmobile.data.repository

import com.example.testmobile.core.Either
import com.example.testmobile.core.Either.Left
import com.example.testmobile.core.Either.Right
import com.example.testmobile.core.Failure
import com.example.testmobile.data.models.Car
import com.example.testmobile.data.models.Category
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity

class CarRepositoryImpl(private val carStoreImpl: CarStore) : CarRepository {

    override fun getAllCars(): Either<Failure, List<CarEntity>> {
        return try {
            var result = carStoreImpl.getAllCars().map { CarToCarEntity(it) }
            Right(result)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun getCar(id: Long): Either<Failure, CarEntity> {
        TODO("Not yet implemented")
    }

    override fun insertCar(car: CarEntity): Either<Failure, Boolean> {
        TODO("Not yet implemented")
    }

    override fun updateCar(car: CarEntity): Either<Failure, Boolean> {
        TODO("Not yet implemented")
    }

    override fun deleteCar(id: Long): Either<Failure, Boolean> {
        TODO("Not yet implemented")
    }

    override fun getCategories(): Either<Failure, List<CategoryEntity>> {
        return try {
            var result = carStoreImpl.getCategories().map { CategorytoEntity(it) }
            Right(result)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun createDefaultCategories(): Either<Failure, Boolean> {
        return try {
            var result = carStoreImpl.getCategories()
            if (result.count() == 0) {
                carStoreImpl.insertDefaultCategories()
            }
            Right(true)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    private fun CarToCarEntity(car: Car): CarEntity {
        return CarEntity(
            car.carId,
            car.seats,
            car.price,
            car.isNew,
            car.model,
            car.isUpdatable,
            car.dateReleased,
            car.categoryId,
            emptyList()
        )
    }

    private fun CategorytoEntity(category: Category): CategoryEntity {
        return CategoryEntity(category.categoryId)
    }

}