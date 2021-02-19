package com.example.testmobile.data.repository

import com.example.testmobile.core.Either
import com.example.testmobile.core.Either.Left
import com.example.testmobile.core.Either.Right
import com.example.testmobile.core.Failure
import com.example.testmobile.data.models.Car
import com.example.testmobile.data.models.CarPropertyCrossRef
import com.example.testmobile.data.models.Category
import com.example.testmobile.data.models.Property
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.entities.PropertyEntity

class CarRepositoryImpl(private val carStoreImpl: CarStore) : CarRepository {

    override fun getAllCars(): Either<Failure, List<CarEntity>> {
        return try {
            var result = carStoreImpl.getAllCars().map { carToCarEntity(it) }
            Right(result)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun getCar(id: Long): Either<Failure, CarEntity> {
        return try {
            val (car, properties) = carStoreImpl.getCar(id)
            var result = carToCarEntity(car)
            result.properties = mutableListOf()
            for (property in properties) {
                result.properties.add(
                    PropertyEntity(
                        property.propertyId,
                        null,
                        null,
                        property.value
                    )
                )
            }
            Right(result)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun insertCar(car: CarEntity): Either<Failure, Boolean> {
        return try {
            carStoreImpl.insertCar(carEntityToCar(car), carEntityToProperties(car))
            Right(true)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun updateCar(car: CarEntity): Either<Failure, Boolean> {
        return try {
            carStoreImpl.updateCar(carEntityToCar(car), carEntityToProperties(car))
            Right(true)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun deleteCar(id: Long): Either<Failure, Boolean> {
        return try {
            carStoreImpl.deleteCar(id)
            Right(true)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun getCategories(): Either<Failure, List<CategoryEntity>> {
        return try {
            var result = carStoreImpl.getCategories().map { categoryToEntity(it) }
            Right(result)
        } catch (exception: Throwable) {
            Left(Failure.DataBaseError)
        }
    }

    override fun getPropertyesByCategory(categoryId: String): Either<Failure, List<PropertyEntity>> {
        return try {
            var result = carStoreImpl.getPropertyByCategory(categoryId).map { propertyToEntity(it) }
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



    //Mappers
    private fun carToCarEntity(car: Car): CarEntity {
        return CarEntity(
            car.carId,
            car.seats,
            car.price,
            car.isNew,
            car.model,
            car.isUpdatable,
            car.dateReleased,
            car.categoryId,
            mutableListOf()
        )
    }

    private fun categoryToEntity(category: Category): CategoryEntity {
        return CategoryEntity(category.categoryId)
    }

    private fun propertyToEntity(property: Property): PropertyEntity {
        return PropertyEntity(
            property.propertyId,
            property.isNumber,
            property.name,
            ""
        )
    }

    private fun carEntityToCar(carEntity: CarEntity): Car {
        return Car(
            carEntity.carId,
            carEntity.seats,
            carEntity.price,
            carEntity.isNew,
            carEntity.model,
            carEntity.isUpdatable,
            carEntity.dateReleased,
            carEntity.categoryId
        )
    }

    private fun carEntityToProperties(carEntity: CarEntity): List<CarPropertyCrossRef> {
        val propertyDB = mutableListOf<CarPropertyCrossRef>()
        for (propertyEntity in carEntity.properties) {
            propertyDB.add(
                CarPropertyCrossRef(
                    0L,
                    propertyEntity.propertyId,
                    propertyEntity.value
                )
            )
        }
        return propertyDB
    }
}