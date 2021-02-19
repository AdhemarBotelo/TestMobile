package com.example.testmobile.features.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testmobile.features.BaseViewModel
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.entities.PropertyEntity
import com.example.testmobile.interactor.useCases.*

class CarViewModel constructor(
    private val getCategories: getCatergories,
    private val getPropertiesByCategory: GetPropertiesByCategory,
    private val insertCar: InsertCar,
    private val updateCar: UpdateCar,
    private val getCar: GetCar
) : BaseViewModel() {
    private val _car: MutableLiveData<CarEntity> = MutableLiveData()
    val car: LiveData<CarEntity> = _car

    private val _categories: MutableLiveData<List<CategoryEntity>> = MutableLiveData()
    val categories: LiveData<List<CategoryEntity>> = _categories

    private val _properties: MutableLiveData<List<PropertyEntity>> = MutableLiveData()
    val properties: LiveData<List<PropertyEntity>> = _properties

    private val _wasProcessed: MutableLiveData<Boolean> = MutableLiveData()
    val wasProcessed: LiveData<Boolean> = _wasProcessed

    fun loadCategories() =
        getCategories(UseCase.None()) { it.fold(::handleFailure, ::handleCategoriersList) }

    fun loadProperties(categoryId: String) =
        getPropertiesByCategory(categoryId) { it.fold(::handleFailure, ::handlePropertiesList) }

    private fun handleCategoriersList(category: List<CategoryEntity>) {
        // in case we need some transformation
        _categories.value = category
    }

    private fun handlePropertiesList(properties: List<PropertyEntity>) {
        _properties.value = properties
    }

    private fun handleProcess(wasProcessed: Boolean) {
        _wasProcessed.value = wasProcessed
    }

    private fun handleGetCar(car: CarEntity) {
        _car.value = car
    }

    fun saveCar(car: CarEntity) {
        insertCar(car) { it.fold(::handleFailure, ::handleProcess) }
    }

    fun updateCar(car: CarEntity) {
        updateCar(car) { it.fold(::handleFailure, ::handleProcess) }
    }

    fun getCar(idCar: Long) {
        getCar(idCar) { it.fold(::handleFailure, ::handleGetCar) }
    }
}