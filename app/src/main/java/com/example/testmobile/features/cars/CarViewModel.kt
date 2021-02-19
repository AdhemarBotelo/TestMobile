package com.example.testmobile.features.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testmobile.features.BaseViewModel
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.entities.PropertyEntity
import com.example.testmobile.interactor.useCases.GetCatergories
import com.example.testmobile.interactor.useCases.GetPropertiesByCategory
import com.example.testmobile.interactor.useCases.InsertCar
import com.example.testmobile.interactor.useCases.UseCase

class CarViewModel constructor(
    private val getCategories: GetCatergories,
    private val getPropertiesByCategory: GetPropertiesByCategory,
    private val insertCar: InsertCar
) : BaseViewModel() {

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

    fun saveCar(car: CarEntity) {
        insertCar(car) { it.fold(::handleFailure, ::handleProcess) }
    }

}