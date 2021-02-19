package com.example.testmobile.features.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testmobile.features.BaseViewModel
import com.example.testmobile.interactor.useCases.GetCars
import com.example.testmobile.interactor.useCases.UseCase
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.useCases.DeleteCar
import com.example.testmobile.interactor.useCases.InitDB

class CarsViewModel
constructor(
    private val getCars: GetCars,
    private val deleteCar: DeleteCar,
    private val initDB: InitDB
) : BaseViewModel() {

    private val _cars: MutableLiveData<List<CarEntity>> = MutableLiveData()
    val cars: LiveData<List<CarEntity>> = _cars

    private val _wasDeleted: MutableLiveData<Boolean> = MutableLiveData()
    val wasDeleted: LiveData<Boolean> = _wasDeleted

    fun loadCars() = getCars(UseCase.None()) { it.fold(::handleFailure, ::handleCarList) }

    fun initDataBase() = initDB(UseCase.None()) { it.fold(::handleFailure, {}) }

    private val _wasProcessed: MutableLiveData<Boolean> = MutableLiveData()
    val wasProcessed: LiveData<Boolean> = _wasProcessed

    private fun handleCarList(cars: List<CarEntity>) {
        // in case we need some transformation
        _cars.value = cars
    }

    private fun handleDelete(wasDeleted: Boolean) {
        // in case we need some transformation
        _wasDeleted.value = wasDeleted
    }

    fun deleteCar(carId: Long) {
        deleteCar(carId) { it.fold(::handleFailure, ::handleDelete) }
    }
}