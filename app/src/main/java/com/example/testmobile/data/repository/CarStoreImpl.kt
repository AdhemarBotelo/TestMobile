package com.example.testmobile.data.repository

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

    override fun getCar(id: Long): CarWithProperties {
        return carDao.getCar(id);
    }

    override fun insertCar(car: Car, property: CarPropertyCrossRef) {
        return carDao.insertCarProperty(car, property)
    }

    override fun updateCar(car: Car, property: Property) {
        TODO("Not yet implemented")
    }

    override fun deleteCar(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getCategories(): List<Category> {
        return carCategory.selectCategories();
    }

    override fun insertDefaultCategories() {
        carCategory.insertCategory(Category("Electric"))
        carCategory.insertCategory(Category("Truck"))
        carCategory.insertCategory(Category("Commercial"))
    }


}