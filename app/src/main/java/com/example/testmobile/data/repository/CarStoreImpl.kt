package com.example.testmobile.data.repository

import com.example.testmobile.core.DefaultCategories
import com.example.testmobile.data.db.CarDao
import com.example.testmobile.data.db.CarsDatabase
import com.example.testmobile.data.db.CategoryDao
import com.example.testmobile.data.db.PropertyDao
import com.example.testmobile.data.models.*

class CarStoreImpl(private val database: CarsDatabase) : CarStore {
    private val carDao: CarDao = database.getCarDao()
    private val carCategory: CategoryDao = database.getCategoryDao()
    private val property: PropertyDao = database.getPropertyDao()

    override fun getAllCars(): List<Car> {
        return carDao.getAllCars();
    }

    override fun getCar(id: Long): Pair<Car, List<CarPropertyCrossRef>> {
        return Pair<Car, List<CarPropertyCrossRef>>(
            carDao.getCar(id),
            carDao.selectPropertyCar(id)
        )
    }

    override fun insertCar(car: Car, property: List<CarPropertyCrossRef>) {
        return carDao.insertCarProperty(car, property)
    }

    override fun updateCar(car: Car, property: List<CarPropertyCrossRef>) {
        return carDao.updateCarProperty(car, property)
    }

    override fun deleteCar(id: Long) {
        carDao.completeDeleteCar(idCar = id)
    }

    override fun getCategories(): List<Category> {
        return carCategory.selectCategories();
    }

    override fun getPropertyByCategory(categoryId: String): List<Property> {
        return property.selectPropertyByCategory(categoryId)
    }

    override fun insertDefaultCategories() {
        for (category in DefaultCategories) {
            carCategory.insertCategory(Category(category))
        }
        insertDefaultPropertiesCategory()
    }

    private fun insertDefaultPropertiesCategory() {
        for (category in DefaultCategories) {
            if (property.selectPropertyByCategory(category).count() == 0) {
                when (category) {
                    "Electric" -> {
                        property.insertProperty(
                            Property(
                                isNumber = true,
                                name = "Batery capacity",
                                propertyCategoryId = category
                            )
                        )
                    }
                    "Truck" -> {
                        property.insertProperty(
                            Property(
                                isNumber = true,
                                name = "Max available payload",
                                propertyCategoryId = category
                            )
                        )
                    }
                    "Commercial" -> {
                        property.insertProperty(
                            Property(
                                isNumber = true,
                                name = "Space capacity",
                                propertyCategoryId = category
                            )
                        )
                    }
                }
            }
        }
    }
}